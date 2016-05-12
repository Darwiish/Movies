package com.example.abdirahman.movielist.DALC;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abdirahman on 26/04/16.
 */
public class DB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "movies.db";

    public static final String TABLE_NAME = "movies";
    // for cursor adapter the table (or the cursor) must include a column named "_id"
    public static final String ID = "_id";
    public static final String TITLE = "title";
    public static final String RELEASE = "release";
    public static final String IMAGEURL = "imageurl";
    public static final String OVERVIEW = "overview";
    public static final String STORED = "stored";

    public static final String[] ALL_COLUMNS = new String[] {ID, TITLE, RELEASE, IMAGEURL, OVERVIEW, STORED};
    public static final String[] LIST_COLUMNS = new String[] {TITLE, RELEASE, IMAGEURL, OVERVIEW, STORED};
    public static final String ID_EQUAL = ID + "=?";

    public static String[] stringArray(long id) {
        return new String[] {Long.toString(id)};
    }
    // SQL create table statement
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" + ID
            + " integer primary key, " + TITLE + " text not null, " + RELEASE
            + " text not null, " + IMAGEURL + " text, " + OVERVIEW + " text, " + STORED + " integer not null" +");";

    DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
}