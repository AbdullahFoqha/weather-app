package com.example.weather.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREFS_NAME = "UserPreferences";
    public static final String LAST_SELECTED_CITY = "lastSelectedCity";

    public static void putInteger(Context context, String key, int val) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, val);
        // Commit the edits!
        editor.apply();

    }

    public static int getInteger(Context context, String key, int defaultValue) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);

        return settings.getInt(key, defaultValue);
    }

    public static void putBoolean(Context context, String key, boolean val) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, val);
        // Commit the edits!
        editor.apply();

    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);

        return settings.getBoolean(key, defaultValue);
    }

    public static void putString(Context context, String key, String val) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, val);
        // Commit the edits!
        editor.apply();

    }

    public static String getString(Context context, String key) {
        return getString(context, key, null);

    }

    public static String getString(Context context, String key,
                                   String defaultValue) {
        SharedPreferences settings = context
                .getSharedPreferences(PREFS_NAME, 0);

        return settings.getString(key, defaultValue);

    }
}