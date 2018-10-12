package com.sky.movielistapp.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Anu on 10/10/2018.
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