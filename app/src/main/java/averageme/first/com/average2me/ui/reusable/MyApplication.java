package averageme.first.com.average2me.ui.reusable;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;

import averageme.first.com.average2me.R;


public class MyApplication extends Application {

    private static Application application;
    public static boolean DEBUG;

    public static Context getContext() {
        return application.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        DEBUG = false /*etResources().getBoolean(1)*/;

        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this,  getResources().getString(R.string.avg_app_id));
    }
}
