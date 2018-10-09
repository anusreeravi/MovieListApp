package com.sky.movielistapp.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sky.movielistapp.models.MovieDBItem;
import com.sky.movielistapp.models.MovieListItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/*
   Database Manager class where values are populated and fetched from database
 */
public class DatabaseManager extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "movie_db";
    // tasks table name
    private static final String TABLE_MOVIES = "movies_table";
    // tasks Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "movie_title";
    private static final String KEY_YEAR = "movie_year";
    private static final String KEY_GENRE = "movie_genre";
    private static final String KEY_POSTER = "movie_image";


    @Inject
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
       Creating table
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //    dbase = db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_MOVIES + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY ," + KEY_TITLE
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



    /*
      Adding details to DB
     */
    public void addMoviesToDB(MovieDBItem details) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
                 ContentValues values = new ContentValues();
                values.put(KEY_ID, details.getId());
                values.put(KEY_TITLE, details.getTitle());
                values.put(KEY_YEAR, details.getYear());
                 values.put(KEY_GENRE, details.getGenre());
                 values.put(KEY_POSTER, details.getPoster());


            // Inserting Row
                db.insert(TABLE_MOVIES, null, values);
            }
         catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
    /*
        Deleting details from DB
       */
    public void deleteMoviesFromDB(int  id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
           String args[]={""+id};
            db.delete(TABLE_MOVIES, null, null);
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /*
      Fetching data from DB
     */
    public List<MovieDBItem> getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<MovieDBItem> movieList = new ArrayList<>();

        try {
    // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;
            Cursor cursor = db.query(TABLE_MOVIES,null, null,null,null,null,null);
    // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    MovieDBItem item = new MovieDBItem();
                    item.setId(cursor.getInt(0));
                    item.setTitle(cursor.getString(1));
                    item.setYear(cursor.getString(2));
                    item.setGenre(cursor.getString(3));
                    item.setPoster(cursor.getString(4));
                    movieList.add(item);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return movieList;
    }

}

