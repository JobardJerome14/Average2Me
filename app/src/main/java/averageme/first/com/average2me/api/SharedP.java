package averageme.first.com.average2me.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import averageme.first.com.average2me.models.Ask;

public class SharedP {
    private Context context;

    public SharedP(Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPref() {
        return this.context.getSharedPreferences(UserPrefHelper.USER_PREF_DICO, Context.MODE_PRIVATE);
    }

    public String getUserId() {
        return getSharedPref().getString(UserPrefHelper.USER_ID, "");
    }

    public void setUserId(String user_id) {
        getSharedPref().edit()
                .putString(UserPrefHelper.USER_ID, user_id)
                .apply();
    }

    public Ask getCurrentAsk() {
        Gson gson = new Gson();
        String json = getSharedPref().getString(UserPrefHelper.USER_CURRENT_ASK, "");
        return gson.fromJson(json, Ask.class);
    }

    public void setCurrentAsk(Ask ask) {
        Gson gson = new Gson();
        String json = gson.toJson(ask);

        getSharedPref().edit()
                .putString(UserPrefHelper.USER_CURRENT_ASK, json)
                .apply();
    }

    public String getCategoryId() {
        return getSharedPref().getString(UserPrefHelper.USER_CATEGORY_ID, "");
    }

    public void setCategoryId(String categoryId) {
        getSharedPref().edit()
                .putString(UserPrefHelper.USER_CATEGORY_ID, categoryId)
                .apply();
    }


    private class UserPrefHelper {
        final static String USER_PREF_DICO = "user_pref";
        final static String USER_ID = "user_id";
        final static String USER_CURRENT_ASK = "user_ask";
        final static String USER_MODE_API = "mode_api";
        final static String USER_CATEGORY_ID = "category_id";
    }
}
