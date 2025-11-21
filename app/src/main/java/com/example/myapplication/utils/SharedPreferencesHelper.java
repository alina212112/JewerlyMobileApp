package com.example.myapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "JewelryAppPrefs";

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveUserSession(String email, String userId) {
        editor.putString("USER_EMAIL", email);
        editor.putString("USER_ID", userId);
        editor.putLong("LOGIN_TIME", System.currentTimeMillis());
        editor.apply();
    }

    public String getUserSession() {
        return sharedPreferences.getString("USER_EMAIL", null);
    }

    public String getUserId() {
        return sharedPreferences.getString("USER_ID", null);
    }

    public void saveUserFullName(String fullName) {
        editor.putString("USER_FULL_NAME", fullName);
        editor.apply();
    }

    public String getUserFullName() {
        return sharedPreferences.getString("USER_FULL_NAME", "Пользователь");
    }

    public void clearUserSession() {
        editor.remove("USER_EMAIL");
        editor.remove("USER_ID");
        editor.remove("USER_FULL_NAME");
        editor.apply();
    }

    public boolean hasUserSession() {
        return getUserSession() != null && getUserId() != null;
    }

    public void setRememberMe(boolean remember) {
        editor.putBoolean("REMEMBER_ME", remember);
        editor.apply();
    }

    public boolean isRememberMe() {
        return sharedPreferences.getBoolean("REMEMBER_ME", false);
    }

    public void saveString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void clearAll() {
        editor.clear();
        editor.apply();
    }
}
