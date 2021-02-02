package com.example.submission4.basisdata;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_MOVIE = "tb_movie";
    static String TABLE_TV = "tb_tv";

    static final class MovieColum implements BaseColumns{
        static String TITLE = "title";
        static String OVERVIEW = "overview";
        static String DATE = "date";
        static String POSTER = "poster";
    }

    static final class tvcolum implements BaseColumns{
        static String TITLE = "title";
        static String OVERVIEW = "overview";
        static String DATE = "date";
        static String POSTER = "poster";
    }

}
