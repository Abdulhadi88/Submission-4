package com.example.submission4.basisdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.submission4.model.TvShow.ResultsTv;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.submission4.basisdata.DatabaseContract.TABLE_TV;
import static com.example.submission4.basisdata.DatabaseContract.tvcolum.TITLE;
import static com.example.submission4.basisdata.DatabaseContract.tvcolum.OVERVIEW;
import static com.example.submission4.basisdata.DatabaseContract.tvcolum.DATE;
import static com.example.submission4.basisdata.DatabaseContract.tvcolum.POSTER;

public class TvHelper {
    private static final String DATABASE_TABLE = TABLE_TV ;
    private static DatabaseHelper dataBaseHelper;
    private static TvHelper INSTANCE;
    private static SQLiteDatabase database;

    private TvHelper(Context context){
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static TvHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close(){
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<ResultsTv> getAllTv(){
        ArrayList<ResultsTv> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        ResultsTv tvitem;
        if (cursor.getCount()>0){
            do {
                tvitem = new ResultsTv();
                tvitem.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvitem.setName(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvitem.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                tvitem.setFirstAirDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                tvitem.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                arrayList.add(tvitem);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ResultsTv getTvById(int id){
        Cursor cursor = database.query(
                DATABASE_TABLE,
                new String[]{_ID, TITLE, OVERVIEW, DATE, POSTER},
                _ID+"=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        ResultsTv resultsItem = new ResultsTv();

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            resultsItem.setId(cursor.getColumnIndex(_ID));
            resultsItem.setName(cursor.getString(cursor.getColumnIndex(TITLE)));
            resultsItem.setOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
            resultsItem.setFirstAirDate(cursor.getString(cursor.getColumnIndex(DATE)));
            resultsItem.setPosterPath(cursor.getString(cursor.getColumnIndex(POSTER)));

            cursor.close();
            return resultsItem;
        }
        return null;
    }

    public long insertTv(ResultsTv resultsItem){
        ContentValues args = new ContentValues();
        args.put(_ID, resultsItem.getId());
        args.put(TITLE, resultsItem.getName());
        args.put(OVERVIEW, resultsItem.getOverview());
        args.put(DATE, resultsItem.getFirstAirDate());
        args.put(POSTER, resultsItem.getPosterPath());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTv(int id){
        return database.delete(DATABASE_TABLE, _ID + "=?", new String[]{String.valueOf(id)});
    }
}
