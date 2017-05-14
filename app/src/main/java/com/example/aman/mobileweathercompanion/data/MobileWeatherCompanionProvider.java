package com.example.aman.mobileweathercompanion.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import static com.example.aman.mobileweathercompanion.data.WeatherContract.BASE_CONTENT_URI;

/**
 * Created by user on 4/28/2017.
 */

public class MobileWeatherCompanionProvider extends ContentProvider {
    private SQLiteDatabase weatherdb;
    //authority
    public static final String CONTENT_AUTHORITY = "com.example.aman.mobileweathercompanion";

    //paths
    public static final String PATH_LONG = "longitude";
    public static final String PATH_LAT = "latitude";
    private static final String TAG = "MobileWeatherProvider";

    public static final Uri LONG_CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_LONG).build();
    public static final Uri LAT_CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_LAT).build();

    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(WeatherContract.CONTENT_AUTHORITY, WeatherContract.WeatherMWC.TABLE_CORDS, WeatherContract.TASKS_LIST);
        uriMatcher.addURI(WeatherContract.CONTENT_AUTHORITY, WeatherContract.WeatherMWC.TABLE_CORDS + "/#", WeatherContract.TASKS_ITEM);
    }



    @Override
    public boolean onCreate() {
        weatherdb = new WeatherDbHelper(getContext()).getWritableDatabase();
        boolean isDbNull = (weatherdb == null);
        Log.i(TAG, "onCreate(): isDbNull: " + isDbNull);
        return (weatherdb != null);
    }


    public Cursor query( Uri uri,  String[] projection, String selection,  String[] selectionArgs, String sortOrder) {
        SQLiteCursor cursor = (SQLiteCursor) weatherdb.query(WeatherDbHelper.TABLE_CORDS, projection, selection,
                selectionArgs, null, null, sortOrder);
        return cursor;
    }


    public String getType( Uri uri) {
        return null;
    }



    public Uri insert( Uri uri,  ContentValues values) {
        long lastRowId = weatherdb.insert(WeatherDbHelper.TABLE_CORDS, WeatherDbHelper.COLUMN_LONG, values);
        Uri lastAddedItem = ContentUris.withAppendedId(BASE_CONTENT_URI, lastRowId);
        return lastAddedItem;
    }


    public int delete( Uri uri,  String selection, String[] selectionArgs) {
        int numberOfDeletedRows = weatherdb.delete(LongLatDB.TABLE_CORDS, selection, selectionArgs);
        return numberOfDeletedRows;
    }

    @Override
    public int update(Uri uri, ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }



    protected static class WeatherDbHelper extends SQLiteOpenHelper implements BaseColumns {
        public static final String BASE_NAME = "MobileWeather.db";
        public static final String TABLE_CORDS = "Coordinates";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_LONG = "Longitude";
        public static final String COLUMN_LAT = "Latitude";
        public WeatherDbHelper(Context context) {
            super(context, BASE_NAME, null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_CORDS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LONG +" TEXT, "+ COLUMN_LAT + " TEXT " +
                    ");";
            db.execSQL(query);
        }


        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_CORDS);
            onCreate(db);
        }
    }

}
