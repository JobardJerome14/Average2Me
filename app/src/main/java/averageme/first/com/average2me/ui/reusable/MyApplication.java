package averageme.first.com.average2me.ui.reusable;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.ads.MobileAds;


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
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
    }
}
