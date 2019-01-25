package averageme.first.com.average2me.ui.reusable;

import android.app.Application;
import android.content.Context;


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
    }
}
