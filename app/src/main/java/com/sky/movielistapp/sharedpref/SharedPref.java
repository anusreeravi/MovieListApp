package com.sky.movielistapp.sharedpref;

import android.content.SharedPreferences;

import javax.inject.Inject;



public class SharedPref {

    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPref(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void putValue(String key, long data) {
        sharedPreferences.edit().putLong(key,data).apply();
    }

    public long getValue(String key) {
        return sharedPreferences.getLong(key,0);
    }
}
