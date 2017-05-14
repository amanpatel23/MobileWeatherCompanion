package com.example.aman.mobileweathercompanion.data;

import android.content.ContentResolver;
import android.net.Uri;

import static android.provider.Settings.NameValueTable.NAME;

/**
 * Created by user on 4/16/2017.
 */

public final class WeatherContract {
    public static final int DATABASE_VERSION = 1;
    public static final String CONTENT_AUTHORITY = "com.example.aman.mobileweathercompanion";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final int TASKS_LIST = 1;
    public static final int TASKS_ITEM = 2;
    //Possible paths so we can append to the base content URI
    public static final String PATH_LONG = "longitude";
    public static final String PATH_LAT = "latitude";


    public static final class WeatherMWC{
        public static final String TABLE_CORDS = "Coordinates";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_LONG = "Longitude";
        public static final String COLUMN_LAT = "Latitude";


        public static final String PATH_WEATHER = "weather";
        public static final String PATH_LOCATION = "location";


        public static final Uri LONG_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LONG).build();
        public static final Uri LAT_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LAT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY;

        public static final String[] PROJECTION_ALL =
                {TABLE_CORDS, COLUMN_ID, COLUMN_LONG, COLUMN_LAT};

        public static final String SORT_ORDER_DEFAULT =
                NAME + " DESC";

    }



}
