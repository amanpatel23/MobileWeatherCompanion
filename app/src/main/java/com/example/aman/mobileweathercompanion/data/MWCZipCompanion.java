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
import android.support.annotation.Nullable;
import android.util.Log;

import static com.example.aman.mobileweathercompanion.data.WeatherContract.BASE_CONTENT_URI;

/**
 * Created by tquart1 on 5/14/2017.
 * Content provider based on Zip database to be more precise and accurate
 */

public class MWCZipCompanion extends ContentProvider {
    private SQLiteDatabase weatherdb;

    //authority
    public static final String CONTENT_AUTHORITY = "com.example.aman.mobileweathercompanion";



    public static final String PATH_ZIP = "Zip_Code";
    private static final String TAG = "MobileWeatherProvider";


    public static final Uri ZIP_CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_ZIP).build();


    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(WeatherContract.CONTENT_AUTHORITY, WeatherContract.WeatherMWC.TABLE_ZIP, WeatherContract.TASKS_LIST);
        uriMatcher.addURI(WeatherContract.CONTENT_AUTHORITY, WeatherContract.WeatherMWC.TABLE_ZIP + "/#", WeatherContract.TASKS_ITEM);
    }

    @Override
    public boolean onCreate() {
        weatherdb = new MWCZipCompanion.WeatherDbHelper(getContext()).getWritableDatabase();
        boolean isDbNull = (weatherdb == null);
        Log.i(TAG, "onCreate(): isDbNull: " + isDbNull);
        return (weatherdb != null);
    }


    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection, String[] selectionArgs,  String sortOrder) {
        SQLiteCursor cursor = (SQLiteCursor) weatherdb.query(WeatherDbHelper.TABLE_ZIPS, projection, selection,
                selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType( Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        long lastRowId = weatherdb.insert(MWCZipCompanion.WeatherDbHelper.TABLE_ZIPS, MWCZipCompanion.WeatherDbHelper.COLUMN_ZIP, values);
        Uri lastAddedItem = ContentUris.withAppendedId(BASE_CONTENT_URI, lastRowId);
        return lastAddedItem;
    }

    @Override
    public int delete( Uri uri, String selection,  String[] selectionArgs) {
        int numberOfDeletedRows = weatherdb.delete(ZipDb.TABLE_ZIPS, selection, selectionArgs);
        return numberOfDeletedRows;
    }

    @Override
    public int update( Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
    protected static class WeatherDbHelper extends SQLiteOpenHelper implements BaseColumns {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "Zip.db";
        public static final String TABLE_ZIPS = "Cordinates";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_ZIP = "zip";;

        public WeatherDbHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_ZIPS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ZIP +" TEXT " +
                    ");";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZIPS);
            onCreate(db);
        }

    }
}
