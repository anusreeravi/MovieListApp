package com.sky.movielistapp.db;

public interface DBConstants {

    public static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "movie_db";
    // table name
    public static final String TABLE_MOVIES = "movies_table";
    // Table Columns names
    public static final String KEY_TABLE_ID = "id";

    public static final String KEY_ID = "movie_id";
    public static final String KEY_TITLE = "movie_title";
    public static final String KEY_YEAR = "movie_year";
    public static final String KEY_GENRE = "movie_genre";
    public static final String KEY_POSTER = "movie_image";
}
