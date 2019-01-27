package averageme.first.com.average2me.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import averageme.first.com.average2me.R;
import averageme.first.com.average2me.api.API;
import averageme.first.com.average2me.api.SharedP;
import averageme.first.com.average2me.api.helpers.ResultatCallback;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ask);

        this.sharedP = new SharedP(this);
        this.user_id = sharedP.getUserId();

        bindView();
    }

    private void bindView() {
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); //TODO ADMOB
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

        this.categoryId = this.sharedP.getCategoryId();
        api.getAverageMeAsk(this.user_id, this.categoryId, new ResultatCallback<AskList>() {
            @Override
            public void onWaitingResult(AskList askList) {
                askList.setAnswered(0);
                save_currentAskList(askList);
                set_shp_reload_api(0);
                load_ask(askList.getAsk(0));
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
        answered = answered + 1; //TODO
        askList.setAnswered(answered);
        this.sharedP.setCurrentAskList(askList);

        if(answered == 3) {
            //need to call api for next question
            this.sharedP.setReloadApi(1);
            load_interstitiel();
        }
        api.updateAverageMeAsk(this.id_ask, response, new ResultatCallback<RetourUpdate>() {
            @Override
            public void onWaitingResult(RetourUpdate result) {
                goto_answer();
            }
        });
    }


    private void load_interstitiel() {
        Integer test = this.sharedP.getReloadApi();
        Log.i("getReloadApi", String.valueOf(test));

        if( test == 1) {
            if (mInterstitialAd.isLoaded()) {
                Log.i("getReloadApi isLoaded", String.valueOf(test));
                mInterstitialAd.show();
            } else {
                LogUtils.log("getReloadApi not Loaded", "The interstitial wasn't loaded yet.");
            }
        }
    }

    private void goto_answer() {
        Bundle bundle = new Bundle();
        bundle.putInt("ask_id", this.id_ask);
        bundle.putString("response", response);
        navigate(AnswerActivity.class, bundle);
    }

    private void load_ask(Ask ask) {
        this.sharedP.setCurrentAsk(ask);
        this.id_ask = ask.getId_ask();
        this.ask_label.setText(ask.getAsk());
        this.btn_a.setText(ask.getReponse_a());
        this.btn_b.setText(ask.getReponse_b());
    }
}
