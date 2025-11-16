package com.example.highcontrols.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtil {

    private static SharedUtil instance;
    private SharedPreferences preferences;

    public static SharedUtil getInstance(Context ctx) {
        if (instance == null) {
            instance = new SharedUtil();
            instance.preferences = ctx.getSharedPreferences("shopping", Context.MODE_PRIVATE);
        }
        return instance;
    }

    public void writeBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean readBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }
}
