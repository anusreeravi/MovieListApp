package com.sky.movielistapp.app;

import android.app.Application;

import dagger.android.HasActivityInjector;


/*
Application class
 */
public class MovieApp extends Application {

    private MovieAppComponent sharedPref;
    private static MovieApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
    }

    public static MovieApp getInstance() {
        return app;
    }


}
