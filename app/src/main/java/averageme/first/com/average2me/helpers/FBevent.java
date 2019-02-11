package averageme.first.com.average2me.helpers;

import android.content.Context;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import averageme.first.com.average2me.api.SharedP;
import averageme.first.com.average2me.ui.reusable.LogUtils;

public class FBevent {

    public FBevent(Context context, String event, String key, String value) {

        FirebaseAnalytics mFirebaseAnalytics;

        SharedP sharedP;

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        sharedP = new SharedP(context);

        Bundle bundle = new Bundle();
        bundle.putString(key, value);
        bundle.putString("user_id", sharedP.getUserId());

        mFirebaseAnalytics.logEvent(event, bundle);

        LogUtils.log("!!!  fb event !!!", event + ' ' + bundle.toString());
    }

}
