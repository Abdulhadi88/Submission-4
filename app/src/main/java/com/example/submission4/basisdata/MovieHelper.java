package com.example.submission4.basisdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.submission4.model.movie.ResultsItem;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.submission4.basisdata.DatabaseContract.MovieColum.DATE;
import static com.example.submission4.basisdata.DatabaseContract.MovieColum.OVERVIEW;
import static com.example.submission4.basisdata.DatabaseContract.MovieColum.POSTER;
import static com.example.submission4.basisdata.DatabaseContract.MovieColum.TITLE;
import static com.example.submission4.basisdata.DatabaseContract.TABLE_MOVIE;

public class MovieHelper {
    private static final String DATABASE_TABLE = TABLE_MOVIE ;
    private static DatabaseHelper dataBaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    private MovieHelper(Context context){
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException{
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close(){
        dataBaseHelper.close();
    if (database.isOpen())
    database.close();
    }

    public ArrayList<ResultsItem> getAllMovies(){
        ArrayList<ResultsItem> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        ResultsItem note;
        if (cursor.getCount()>0){
         do {
             note = new ResultsItem();
             note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
             note.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
             note.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
             note.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
             note.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
             arrayList.add(note);
             cursor.moveToNext();
         }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ResultsItem getMovieById(int id){
        Cursor cursor = database.query(
                DATABASE_TABLE,
                new String[]{_ID, TITLE, OVERVIEW, DATE, POSTER},
                _ID+"=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        ResultsItem resultsItem = new ResultsItem();

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            resultsItem.setId(cursor.getColumnIndex(_ID));
            resultsItem.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
            resultsItem.setReleaseDate(cursor.getString(cursor.getColumnIndex(DATE)));
            resultsItem.setPosterPath(cursor.getString(cursor.getColumnIndex(POSTER)));

            cursor.close();
            return resultsItem;
        }
        return null;
    }

    public long insertMovie(ResultsItem resultsItem){
        ContentValues args = new ContentValues();
        args.put(_ID, resultsItem.getId());
        args.put(TITLE, resultsItem.getTitle());
        args.put(OVERVIEW, resultsItem.getOverview());
        args.put(DATE, resultsItem.getReleaseDate());
        args.put(POSTER, resultsItem.getPosterPath());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMovie(int id){
        return database.delete(DATABASE_TABLE, _ID + "=?", new String[]{String.valueOf(id)});
    }
}
