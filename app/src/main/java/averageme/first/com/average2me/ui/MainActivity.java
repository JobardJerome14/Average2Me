package averageme.first.com.average2me.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.UUID;
import averageme.first.com.average2me.R;
import averageme.first.com.average2me.api.SharedP;
import averageme.first.com.average2me.helpers.FBevent;
import averageme.first.com.average2me.helpers.IFBEvent;
import averageme.first.com.average2me.ui.reusable.ActivityBase;
import averageme.first.com.average2me.ui.reusable.LogUtils;


public class MainActivity extends ActivityBase {

    Button politic_btn;
    Button sport_btn;
    Button food_btn;
    Button hobby_btn;
    Button society_btn;
    Button medley_btn;
    SharedP sharedP;

    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.sharedP = new SharedP(this);
        if (this.sharedP.getUserId().isEmpty()) {
            this.sharedP.setUserId(UUID.randomUUID().toString());
        }

        this.sharedP.setReloadApi(1);
        bindView();
    }

    private void bindView() {
        this.politic_btn = findViewById(R.id.politic_btn);
        this.politic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_average("3");

            }
        });

        this.sport_btn = findViewById(R.id.sport_btn);
        this.sport_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_average("1");
            }
        });

        this.food_btn = findViewById(R.id.food_btn);
        this.food_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_average("2");
            }
        });

        this.hobby_btn = findViewById(R.id.hobby_btn);
        this.hobby_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_average("4");
            }
        });

        this.society_btn = findViewById(R.id.society_btn);
        this.society_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_average("5");
            }
        });


        this.medley_btn = findViewById(R.id.medley_btn);
        this.medley_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_average("0");
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }


    public void play_average(String categoryId) {
        this.sharedP.setCategoryId(categoryId);
        LogUtils.log("FBEvent", IFBEvent.CLICK_EVENT+ " " + IFBEvent.BUTTON_KEY + " " + categoryId);
        new FBevent(this, IFBEvent.CLICK_EVENT, IFBEvent.BUTTON_KEY, "main_menu_"+categoryId);
        navigate(AskActivity.class, null);


    }

}
