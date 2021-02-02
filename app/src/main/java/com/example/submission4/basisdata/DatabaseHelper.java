package com.example.submission4.basisdata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbmovie";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);}

      private static final String SQL_CREATE_TABLE_MOVIE  = String.format("CREATE TABLE %s" +
              "(%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
              "%s TEXT NOT NULL," +
              "%s TEXT NOT NULL," +
              "%s TEXT NOT NULL," +
              "%s TEXT NOT NULL)",
              DatabaseContract.TABLE_MOVIE,
              DatabaseContract.MovieColum._ID,
              DatabaseContract.MovieColum.TITLE,
              DatabaseContract.MovieColum.DATE,
              DatabaseContract.MovieColum.OVERVIEW,
              DatabaseContract.MovieColum.POSTER
              );
    private static final String SQL_CREATE_TABLE_TV  = String.format("CREATE TABLE %s" +
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            DatabaseContract.TABLE_TV,
            DatabaseContract.tvcolum._ID,
            DatabaseContract.tvcolum.TITLE,
            DatabaseContract.tvcolum.DATE,
            DatabaseContract.tvcolum.OVERVIEW,
            DatabaseContract.tvcolum.POSTER
    );


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TV);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + DatabaseContract.TABLE_MOVIE);
        db.execSQL(" DROP TABLE IF EXISTS " + DatabaseContract.TABLE_TV);
        onCreate(db);
    }
}
