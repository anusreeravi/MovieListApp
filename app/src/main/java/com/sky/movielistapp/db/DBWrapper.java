package com.sky.movielistapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sky.movielistapp.models.MovieDBItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DBWrapper implements DBConstants{



    private DBManager dbManager;
    private SQLiteDatabase db;

    @Inject
    public DBWrapper(Context context){
        dbManager = new DBManager(context);
    }

    public void setUpDB(Context context){
        dbManager = new DBManager(context);
    }
    /*
     Adding details to DB
    */
    public void addMoviesToDB(List<MovieDBItem> movieList) {
        SQLiteDatabase db = dbManager.getWritableDB();
        int i=-1;
        try {
            for (MovieDBItem details:movieList){
                ContentValues values = new ContentValues();
                values.put(KEY_TABLE_ID, i++);
                values.put(KEY_ID, details.getId());
                values.put(KEY_TITLE, details.getTitle());
                values.put(KEY_YEAR, details.getYear());
                values.put(KEY_GENRE, details.getGenre());
                values.put(KEY_POSTER, details.getPoster());

                // Inserting Row
                db.insert(TABLE_MOVIES, null, values);
            }
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
    public void deleteMoviesFromDB() {
        SQLiteDatabase db = dbManager.getWritableDB();
        try {
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
        SQLiteDatabase db = dbManager.getReadableDB();
        List<MovieDBItem> movieList = new ArrayList<>();

        try {

            Cursor cursor = db.query(TABLE_MOVIES,null, null,null,null,null,null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    MovieDBItem item = new MovieDBItem();
                    item.setId(cursor.getInt(1));
                    item.setTitle(cursor.getString(2));
                    item.setYear(cursor.getString(3));
                    item.setGenre(cursor.getString(4));
                    item.setPoster(cursor.getString(5));
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
    public List<MovieDBItem> searchMovies(String searchText) {
        SQLiteDatabase db = dbManager.getReadableDB();
        List<MovieDBItem> movieList = new ArrayList<>();

        try {
            String selection = KEY_TITLE + " LIKE '%"+searchText+"%' OR "
                    + KEY_GENRE + " LIKE '%"+searchText+"%'";
            Cursor cursor = db.query(TABLE_MOVIES,null, selection,null,null,null,null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    MovieDBItem item = new MovieDBItem();
                    item.setId(cursor.getInt(1));
                    item.setTitle(cursor.getString(2));
                    item.setYear(cursor.getString(3));
                    item.setGenre(cursor.getString(4));
                    item.setPoster(cursor.getString(5));
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
