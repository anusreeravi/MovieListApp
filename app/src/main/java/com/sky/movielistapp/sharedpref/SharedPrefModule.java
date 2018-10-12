package com.sky.movielistapp.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Anu on 10/10/2018.
 */

@Module
public class SharedPrefModule {

    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("MovieSharedPref",Context.MODE_PRIVATE);
    }
}