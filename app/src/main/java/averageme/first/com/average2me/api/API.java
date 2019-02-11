package averageme.first.com.average2me.api;

import android.support.annotation.NonNull;
import android.widget.Toast;
import averageme.first.com.average2me.R;
import averageme.first.com.average2me.api.helpers.ResultatCallback;
import averageme.first.com.average2me.models.AskList;
import averageme.first.com.average2me.models.RetourUpdate;
import averageme.first.com.average2me.ui.reusable.MyApplication;
import averageme.first.com.average2me.helpers.FBevent;
import averageme.first.com.average2me.helpers.IFBEvent;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class API {

    private final IApi iApi;

    public API() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        //String url = "http://10.0.2.2:80";
        String url = "https://radiant-anchorage-49837.herokuapp.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.iApi = retrofit.create(IApi.class);

    }

    public void getAverageMeAsk(String user_id, String category, final ResultatCallback<AskList> resultCallback) {
        Call<AskList> call = this.iApi.getAverageMeAsk(user_id, category);
        call.enqueue(new Callback<AskList>() {
            @Override
            public void onResponse(@NonNull Call<AskList> call, @NonNull Response<AskList> response) {
                if (response.isSuccessful()) {
                    AskList askList = response.body();
                    resultCallback.onWaitingResult(askList);
                } else {
                    report_fb_API_crash(IFBEvent.API_GET_AVERAGE_ME_ASK_KEY, IFBEvent.API_ON_RESPONSE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AskList> call, @NonNull Throwable t) {
                report_fb_API_crash(IFBEvent.API_GET_AVERAGE_ME_ASK_KEY, IFBEvent.API_ON_FAILURE);
                Toast.makeText(MyApplication.getContext(), R.string.api_new_game_impossible, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateAverageMeAsk(Integer ask_id, String rep, final ResultatCallback<RetourUpdate> resultCallback) {
        Call<RetourUpdate> call = this.iApi.updateAverageMeAsk(ask_id, rep);
        call.enqueue(new Callback<RetourUpdate>() {
            @Override
            public void onResponse(@NonNull Call<RetourUpdate> call, @NonNull Response<RetourUpdate> response) {
                if (response.isSuccessful()) {
                    RetourUpdate retourUpdate = response.body();
                    resultCallback.onWaitingResult(retourUpdate);
                } else {
                    report_fb_API_crash(IFBEvent.API_UPDATE_AVERAGE_ME_ASK_KEY, IFBEvent.API_ON_RESPONSE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RetourUpdate> call, @NonNull Throwable t) {
                report_fb_API_crash(IFBEvent.API_UPDATE_AVERAGE_ME_ASK_KEY, IFBEvent.API_ON_FAILURE);
                Toast.makeText(MyApplication.getContext(), R.string.api_new_game_impossible, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void report_fb_API_crash(String key, String value) {
        new FBevent(MyApplication.getContext(), IFBEvent.CRASH_EVENT, key, value);
    }

}

