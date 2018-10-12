package com.sky.movielistapp.db;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Module class for DB Dependency Injection
 */

@Module
public class DBModule {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "movie_db";
    @Provides
    DBWrapper provideDB(Context context) {
        return  new DBWrapper(context);

    }

}