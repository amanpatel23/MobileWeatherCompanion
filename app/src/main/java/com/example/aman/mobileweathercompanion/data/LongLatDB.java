package com.example.aman.mobileweathercompanion.data;

/**
 * Created by colep on 4/10/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LongLatDB extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LongLat.db";
    public static final String TABLE_CORDS = "Cordinates";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LONG = "Longitude";
    public static final String COLUMN_LAT = "Latitude";

    //We need to pass database information along to superclass
    public LongLatDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_CORDS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LONG +" TEXT, "+ COLUMN_LAT+ " TEXT " +
                ");";
        db.execSQL(query);
    }
    //
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CORDS);
        onCreate(db);
    }

    //Add a new row to the database
    public void addCord(LongLat longlats){
        ContentValues values = new ContentValues();
        values.put(COLUMN_LONG, longlats.getLongitude());
        values.put(COLUMN_LAT, longlats.getLatitude());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CORDS, null, values);
        db.close();
    }

    //Delete a product from the database
    public void deleteCord(String longi){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CORDS + " WHERE " + COLUMN_LONG + "=\"" + longi + "\";");
    }

    // this is goint in record_TextView in the Main activity.
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CORDS + " WHERE 1";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Longitude")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("Longitude"));
                dbString += recordSet.getString(recordSet.getColumnIndex("Latitude"));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }

}
