package com.example.aman.mobileweathercompanion.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by colep on 4/24/2017.
 */

public class ZipDb extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Zip.db";
    public static final String TABLE_ZIPS = "Cordinates";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ZIP = "zip";;

    //We need to pass database information along to superclass
    public ZipDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_ZIPS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ZIP +" TEXT " +
                ");";
        db.execSQL(query);
    }
    //
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZIPS);
        onCreate(db);
    }

    //Add a new row to the database
    public void addCord(Zip zip){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ZIP, zip.getZipString());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ZIPS, null, values);
        db.close();
    }

    //Delete a product from the database
    public void deleteCord(String longi){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ZIPS + " WHERE " + COLUMN_ZIP + "=\"" + longi + "\";");
    }

    public Zip getTopData(){
        Zip temp = new Zip();
        String zip = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ZIPS + " WHERE 1";// why not leave out the WHERE  clause?
        Cursor recordSet = db.rawQuery(query, null);
        recordSet.moveToFirst();
       /* while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Longitude")) != null) {
                lon = recordSet.getString(recordSet.getColumnIndex("Longitude"));
                lat += recordSet.getString(recordSet.getColumnIndex("Latitude"));
            }
            recordSet.moveToNext();
        }*/
        if (!recordSet.isAfterLast()) {
            if (recordSet.getString(recordSet.getColumnIndex("zip")) != null) {
                zip = recordSet.getString(recordSet.getColumnIndex("zip"));
            } else {


            }
        }
        recordSet.close();
        db.close();
        temp = new Zip(zip);
        return temp;
    }

    public ArrayList<Zip> getData(){
        Zip temp = new Zip();
        ArrayList <Zip> re = new ArrayList<Zip>();
        String Zip = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ZIPS + " WHERE 1";// why not leave out the WHERE  clause?
        Cursor recordSet = null;
        try{
            recordSet = db.rawQuery(query, null);
            recordSet.moveToFirst();
        }
        catch (Exception e){
            String query2 = "CREATE TABLE " + TABLE_ZIPS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ZIP +" TEXT " +
                    ");";
            db.execSQL(query2);

        }

        while (!recordSet.isAfterLast()) {
            if (recordSet.getString(recordSet.getColumnIndex("zip")) != null) {
                Zip = recordSet.getString(recordSet.getColumnIndex("zip"));
                try {
                    temp = new Zip(Zip);
                    //if(!(contains2(re, temp))) {
                        re.add(temp);
                   // }

                }
                catch (Exception e){

                }
            }
            recordSet.moveToNext();
        }
        recordSet.close();
        /*if (!recordSet.isAfterLast()) {
            if (recordSet.getString(recordSet.getColumnIndex("Longitude")) != null) {
                lon = recordSet.getString(recordSet.getColumnIndex("Longitude"));
                lat += recordSet.getString(recordSet.getColumnIndex("Latitude"));
            } else {
                System.out.println("I messed up going back to Towson");
                lon = "-76.6092";
                lat = "39.3938";

            }
        }
        db.close();
        temp.setLongitude(Double.parseDouble(lon));
        temp.setLatitude(Double.parseDouble(lat));
        return temp;*/

        db.close();
        return re;
    }

    boolean contains(ArrayList<LongLat> list, LongLat temp) {
        for (LongLat item : list) {
            if (item.equals(temp)) {
                return true;
            }
        }
        return false;
    }

    boolean contains2(ArrayList<Zip> list, Zip temp) {
        for (Zip item : list) {
            if (item.equals(temp)) {
                return true;
            }
        }
        return false;
    }

    // this is goint in record_TextView in the Main activity.
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ZIPS + " WHERE 1";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Longitude")) != null) {
                dbString += recordSet.getString(recordSet.getColumnIndex("Longitude"))+", ";
                dbString += recordSet.getString(recordSet.getColumnIndex("Latitude"));
                dbString += "\n";
            }
            recordSet.moveToNext();
        }
        db.close();

        return dbString;
    }

}