package com.example.abdirahman.movielist.MovieDALC;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.abdirahman.movielist.Model.Movie;


public class DAO {
    SQLiteDatabase db;
    private static DAO instance;

    private DAO(Context context) {
        db = new DB(context).getWritableDatabase();
    }

    public static DAO getInstance() {
        return instance;
    }

    public static void createInstance(Context context){
        instance = new DAO(context);
    }

    public long addMovie(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(DB.ID, movie.getId());
        values.put(DB.TITLE, movie.getTitle());
        values.put(DB.RELEASE, movie.getRelease());
        values.put(DB.IMAGEURL, movie.getImageURL());
        values.put(DB.OVERVIEW, movie.getOverview());

        if (movie.isStored()) {
            values.put(DB.STORED, 1);
        } else {
            values.put(DB.STORED, 0);
        }
        return db.insert(DB.TABLE_NAME, null, values);
    }

    public int updateMovie(long id, Movie movie) {
        ContentValues values = new ContentValues();
        values.put(DB.TITLE, movie.getTitle());
        values.put(DB.RELEASE, movie.getRelease());
        values.put(DB.IMAGEURL, movie.getImageURL());
        values.put(DB.OVERVIEW, movie.getOverview());
        if (movie.isStored()) {
            values.put(DB.STORED, 1);
        } else {
            values.put(DB.STORED, 0);
        }
        return db.update(DB.TABLE_NAME, values, DB.ID_EQUAL, DB.stringArray(id));
    }

    public Cursor getMovies() {
        return db.query(DB.TABLE_NAME, DB.ALL_COLUMNS, null, null, null, null,
                null);
    }

    public Movie getMovie(long id) {
        Cursor c = db.query(DB.TABLE_NAME, DB.ALL_COLUMNS, DB.ID_EQUAL,
                DB.stringArray(id), null, null, null);
        if (c.moveToFirst()) {
            int Id = c.getInt(c.getColumnIndex(DB.ID));
            String title = c.getString(c.getColumnIndexOrThrow(DB.TITLE));
            String release = c.getString(c
                    .getColumnIndexOrThrow(DB.RELEASE));
            String imageurl = c.getString(c.getColumnIndexOrThrow(DB.IMAGEURL));
            String overview = c.getString(c.getColumnIndexOrThrow(DB.OVERVIEW));
            boolean stored = c.getInt(c.getColumnIndex(DB.STORED)) > 0;
            return new Movie(Id, title, release, overview, imageurl, stored);
        } else
            return null;
    }


    public void close() {
        db.close();
    }
}

