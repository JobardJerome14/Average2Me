package averageme.first.com.average2me.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import averageme.first.com.average2me.R;
import averageme.first.com.average2me.api.API;
import averageme.first.com.average2me.api.SharedP;
import averageme.first.com.average2me.api.helpers.ResultatCallback;
import averageme.first.com.average2me.helpers.FBevent;
import averageme.first.com.average2me.helpers.IFBEvent;
import averageme.first.com.average2me.models.Ask;
import averageme.first.com.average2me.models.AskList;
import averageme.first.com.average2me.models.RetourUpdate;
import averageme.first.com.average2me.ui.reusable.ActivityBase;
import averageme.first.com.average2me.ui.reusable.LogUtils;


public class AskActivity extends ActivityBase {

    String categoryId;

    private String user_id = "JJUSER";
    private String response = "a";
    private SharedP sharedP;
    private Integer id_ask = 0;
    private TextView ask_label;
    private Button btn_a;
    private Button btn_b;

    private AdView mAdView;

    private InterstitialAd mInterstitialAd;
    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ask);

        this.sharedP = new SharedP(this);
        this.user_id = sharedP.getUserId();

        bindView();
    }

    private void bindView() {
        this.spinner = findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.avg_interstitial));
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build());



        this.ask_label = findViewById(R.id.ask_label);

        this.btn_a = findViewById(R.id.btn_a);
        btn_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upd_grille_via_api("a");
            }
        });

        this.btn_b = findViewById(R.id.btn_b);
        btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upd_grille_via_api("b");
            }
        });

        if(this.sharedP.getReloadApi() == 1) {
            get_grille_via_api();
        } else {
            AskList askList = this.sharedP.getCurrentAskList();
            Integer id = askList.getAnswered();
            load_ask(askList.getAsk(id));
        }

    }


    private void get_grille_via_api() {
        API api = new API();
        spinner.setVisibility(View.VISIBLE);
        this.categoryId = this.sharedP.getCategoryId();
        api.getAverageMeAsk(this.user_id, this.categoryId, new ResultatCallback<AskList>() {
            @Override
            public void onWaitingResult(AskList askList) {
                askList.setAnswered(0);
                save_currentAskList(askList);
                set_shp_reload_api(0);
                load_ask(askList.getAsk(0));
                spinner.setVisibility(View.GONE);
            }
        });
    }

    private void save_currentAskList(AskList askList) {
        this.sharedP.setCurrentAskList(askList);
    }

    private void set_shp_reload_api(Integer reloadApi) {
        this.sharedP.setReloadApi(reloadApi);
    }


    private void upd_grille_via_api(String response) {
        API api = new API();
        this.response = response;
        AskList askList = this.sharedP.getCurrentAskList();
        Integer answered = askList.getAnswered();
        answered = answered + 1;
        askList.setAnswered(answered);
        this.sharedP.setCurrentAskList(askList);

        if(answered == 4) { //TODO reload API after 4 answers
            //need to call api for next question
            this.sharedP.setReloadApi(1);
            load_interstitial();
        }
        api.updateAverageMeAsk(this.id_ask, response, new ResultatCallback<RetourUpdate>() {
            @Override
            public void onWaitingResult(RetourUpdate result) {
                goto_answer();
            }
        });
    }


    private void load_interstitial() {
        Integer test = this.sharedP.getReloadApi();
        Log.i("getReloadApi", String.valueOf(test));

        if( test == 1) {
            if (mInterstitialAd.isLoaded()) {
                Log.i("getReloadApi isLoaded", String.valueOf(test));
                LogUtils.log("FBEvent", IFBEvent.LOAD_EVENT+ " " + IFBEvent.INTERSTITIAL_KEY + " ask_interstitial_isLoaded");
                new FBevent(this, IFBEvent.LOAD_EVENT, IFBEvent.INTERSTITIAL_KEY, "ask_interstitial_isLoaded");
                mInterstitialAd.show();
            } else {
                Log.d("getReloadApi", "not loaded");
                LogUtils.log("FBEvent", IFBEvent.LOAD_EVENT+ " " + IFBEvent.INTERSTITIAL_KEY + " ask_interstitial_notLoaded");
                new FBevent(this, IFBEvent.LOAD_EVENT, IFBEvent.INTERSTITIAL_KEY, "ask_interstitial_notLoaded");
            }
        } else {
            Log.d("getReloadApi", "test not equal to 1");
        }
    }

    private void goto_answer() {
        Bundle bundle = new Bundle();
        bundle.putInt("ask_id", this.id_ask);
        bundle.putString("response", response);
        navigate(AnswerActivity.class, bundle);

        LogUtils.log("FBEvent", IFBEvent.CLICK_EVENT+ " " + IFBEvent.BUTTON_KEY + " " + response);
        new FBevent(this, IFBEvent.CLICK_EVENT, IFBEvent.BUTTON_KEY, "ask_response_"+response);
    }

    private void load_ask(Ask ask) {
        if(ask != null) {
            this.sharedP.setCurrentAsk(ask);
            this.id_ask = ask.getId_ask();
            this.ask_label.setText(ask.getAsk());
            this.btn_a.setText(ask.getReponse_a());
            this.btn_b.setText(ask.getReponse_b());
        }
    }
}
