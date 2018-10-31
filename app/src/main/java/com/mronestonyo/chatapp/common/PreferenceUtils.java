package com.mronestonyo.chatapp.common;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {

    private static final String APP_ID = "chatapp";

    public static String getStringPreference(Context context, String key) {
        String value = "";
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        value = prefs.getString(key, "");
        return value;
    }

    public static void clearPreference(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

    //remove key
    public static void removePreference(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key);
        editor.apply();
    }

    public static void saveStringPreference(Context context, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void saveBooleanPreference(Context context, String key, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBooleanPreference(Context context, String key) {
        boolean value = false;
        SharedPreferences prefs = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE);
        value = prefs.getBoolean(key, false);
        return value;
    }

}
