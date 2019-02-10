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


public class AskActivity extends ActivityBase /*implements RewardedVideoAdListener*/ {

    String categoryId;

    private String user_id = "JJUSER";
    private String response = "a";
    private SharedP sharedP;
    private Integer id_ask = 0;
    private TextView ask_label;
    TextView ask_category_label;
    private Button btn_a;
    private Button btn_b;

    private AdView mAdView;

    private InterstitialAd mInterstitialAd;
    private ProgressBar spinner;

    //private RewardedVideoAd mAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ask);

/*        mAd = MobileAds.getRewardedVideoAdInstance(this);
        mAd.setRewardedVideoAdListener(this);
        mAd.loadAd(getResources().getString(R.string.avg_video), new AdRequest.Builder().build());*/

        this.sharedP = new SharedP(this);
        this.user_id = sharedP.getUserId();

        bindView();
    }

/*    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        goto_answer();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }*/

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


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                goto_answer();
            }
        });

        this.ask_label = findViewById(R.id.ask_label);

        this.ask_category_label = findViewById(R.id.ask_category_label);
        String cat_id = this.sharedP.getCategoryId();
        switch(cat_id) {
            case "1":
                cat_id = getResources().getString(R.string.sport_btn_label);
                break;
            case "2":
                cat_id = getResources().getString(R.string.food_btn_label);
                break;
            case "3":
                cat_id = getResources().getString(R.string.politique_btn_label);
                break;
            case "4":
                cat_id = getResources().getString(R.string.loisir_btn_label);
                break;
            case "5":
                cat_id = getResources().getString(R.string.society_btn_label);
                break;
            default:
                cat_id = getResources().getString(R.string.medley_btn_label);
                break;
        }


        this.ask_category_label.setText(String.valueOf(cat_id));

        this.btn_a = findViewById(R.id.btn_a);
        btn_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upd_grille_via_api("a");
            }
        });
        btn_a.setVisibility(View.VISIBLE);

        this.btn_b = findViewById(R.id.btn_b);
        btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upd_grille_via_api("b");
            }
        });
        btn_b.setVisibility(View.VISIBLE);

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
        btn_a.setVisibility(View.GONE);
        btn_b.setVisibility(View.GONE);
        this.categoryId = this.sharedP.getCategoryId();
        api.getAverageMeAsk(this.user_id, this.categoryId, new ResultatCallback<AskList>() {
            @Override
            public void onWaitingResult(AskList askList) {
                askList.setAnswered(0);
                save_currentAskList(askList);
                set_shp_reload_api(0);
                load_ask(askList.getAsk(0));
                spinner.setVisibility(View.GONE);
                btn_a.setVisibility(View.VISIBLE);
                btn_b.setVisibility(View.VISIBLE);
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

        if(answered == 4) {
            //need to call api for next question
            set_shp_reload_api(1);
            load_interstitial();
            //load_video();
        }
        api.updateAverageMeAsk(this.id_ask, response, new ResultatCallback<RetourUpdate>() {
            @Override
            public void onWaitingResult(RetourUpdate result) {
                is_going_to_answer();//goto_answer();
            }
        });
    }

    private void is_going_to_answer() {
        if(this.sharedP.getReloadApi() == 0) {
            goto_answer();
        }
    }


/*    private void load_video() {
        Integer test = this.sharedP.getReloadApi();
        Log.i("load_video", String.valueOf(test));

        if( test == 1) {
            if (mAd.isLoaded()) {
                Log.i("load_video isLoaded", String.valueOf(test));
                LogUtils.log("FBEvent", IFBEvent.LOAD_EVENT+ " " + IFBEvent.INTERSTITIAL_KEY + " ask_video_isLoaded");
                new FBevent(this, IFBEvent.LOAD_EVENT, IFBEvent.INTERSTITIAL_KEY, "ask_video_isLoaded");
                mAd.show();
            } else {
                Log.d("load_video", "not loaded");
                LogUtils.log("FBEvent", IFBEvent.LOAD_EVENT+ " " + IFBEvent.INTERSTITIAL_KEY + " ask_video_notLoaded");
                new FBevent(this, IFBEvent.LOAD_EVENT, IFBEvent.INTERSTITIAL_KEY, "ask_video_notLoaded");
                goto_answer();
            }
        } else {
            Log.d("load_video", "test not equal to 1");
            goto_answer();
        }
    }*/


    private void load_interstitial() {
        Integer test = this.sharedP.getReloadApi();
        Log.i("load_interstitial", String.valueOf(test));

        if( test == 1) {
            if (mInterstitialAd.isLoaded()) {
                Log.i("load isLoaded", String.valueOf(test));
                LogUtils.log("FBEvent", IFBEvent.LOAD_EVENT+ " " + IFBEvent.INTERSTITIAL_KEY + " ask_interstitial_isLoaded");
                new FBevent(this, IFBEvent.LOAD_EVENT, IFBEvent.INTERSTITIAL_KEY, "ask_interstitial_isLoaded");
                mInterstitialAd.show();

            } else {
                Log.d("load_interstitial", "not loaded");
                LogUtils.log("FBEvent", IFBEvent.LOAD_EVENT+ " " + IFBEvent.INTERSTITIAL_KEY + " ask_interstitial_notLoaded");
                new FBevent(this, IFBEvent.LOAD_EVENT, IFBEvent.INTERSTITIAL_KEY, "ask_interstitial_notLoaded");
                goto_answer();
            }
        } else {
            Log.d("load_interstitial", "test not equal to 1");
            goto_answer();
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
