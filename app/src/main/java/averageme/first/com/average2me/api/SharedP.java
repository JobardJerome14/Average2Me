package averageme.first.com.average2me.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

import averageme.first.com.average2me.models.Ask;
import averageme.first.com.average2me.models.AskList;

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


    public AskList getCurrentAskList() {
        Gson gson = new Gson();
        String json = getSharedPref().getString(UserPrefHelper.USER_CURRENT_ASK_LIST, "");
        return gson.fromJson(json, AskList.class);
    }


    public void setCurrentAskList(AskList askList) {
        Gson gson = new Gson();
        String json = gson.toJson(askList);

        getSharedPref().edit()
                .putString(UserPrefHelper.USER_CURRENT_ASK_LIST, json)
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


    public Integer getReloadApi() {
        return getSharedPref().getInt(UserPrefHelper.USER_RELOAD_API, 0);
    }

    public void setReloadApi(Integer reload) {
        getSharedPref().edit()
                .putInt(UserPrefHelper.USER_RELOAD_API, reload)
                .apply();
    }


    private class UserPrefHelper {
        final static String USER_PREF_DICO = "user_pref";
        final static String USER_ID = "user_id";
        final static String USER_CURRENT_ASK = "user_ask";
        final static String USER_CURRENT_ASK_LIST = "user_ask_list";
        final static String USER_CATEGORY_ID = "category_id";
        final static String USER_RELOAD_API = "reload_api";
    }
}
