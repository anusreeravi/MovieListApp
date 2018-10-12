package com.sky.movielistapp.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sky.movielistapp.models.MovieDBItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
   Database Manager class where values are populated and fetched from database
 */
public class DBManager extends SQLiteOpenHelper implements DBConstants{

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
       Creating table
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //    dbase = db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_MOVIES + " ( "
                + KEY_TABLE_ID + " INTEGER PRIMARY KEY ,"
                + KEY_ID + " INTEGER ," + KEY_TITLE
                + " TEXT, " + KEY_YEAR
                + " TEXT, " + KEY_GENRE
                + " TEXT," + KEY_POSTER
                + " TEXT)";
        db.execSQL(sql);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
    // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
    // Create tables again
        onCreate(db);
    }

    public SQLiteDatabase getReadableDB(){
        return getReadableDatabase();
    }

    public SQLiteDatabase getWritableDB(){
        return getWritableDatabase();
    }

}

