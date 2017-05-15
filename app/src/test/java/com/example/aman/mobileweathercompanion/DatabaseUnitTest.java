package com.example.aman.mobileweathercompanion;


import android.content.ContentProvider;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.aman.mobileweathercompanion.data.WeatherContract;
import com.example.aman.mobileweathercompanion.data.Zip;
import com.example.aman.mobileweathercompanion.data.ZipDb;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by user on 5/14/2017.
 */

@LargeTest
public class DatabaseUnitTest extends AndroidTestCase{
    //Provided tests for both zip database and long lat content provider

    private ZipDb zip;
    private ContentProvider provider;

    public void setUp() throws Exception {
        super.setUp();

    }
    public void testPreConditions() {
        assertNotNull(zip);
    }

    public void testAddCord(){
        Zip zipped = null;
        zip.addCord(zipped);
        List<Zip> zips = zip.getData();
        assertThat(zips.size(), is(1));

    }
    public void testDeleteCord(){
        zip.deleteCord("20706");
        List<Zip> zips = zip.getData();

        assertThat(zips.size(), is(0));
    }
    public void testQuery(){
        ContentProvider provider = getProvider();
        Uri uri = WeatherContract.BASE_CONTENT_URI;

        Cursor cursor = provider.query(uri, null, null, null, null);

        assertNotNull(cursor);

        cursor = null;
        try{
            cursor = provider.query(Uri.parse("weather_contract"), null, null, null, null);

            fail();
        }catch(IllegalArgumentException e){
            assertTrue(true);
        }

    }



    public void tearDown() {
        zip.close();
    }


    public ContentProvider getProvider() {
        return provider;
    }
}
