package com.example.aman.mobileweathercompanion;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP_MR1)
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    private Activity activity;
    private EditText editLoc;


    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
        editLoc = (EditText) activity.findViewById(R.id.editZip);
    }

    @Test
    public void shouldNotShowPrecipChanceLabelOnStartup() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        TextView precipChance = (TextView) activity.findViewById(R.id.percipChance);
        assertNotNull(precipChance);
        assertEquals(View.INVISIBLE, precipChance.getVisibility());
    }


    @Test
    public void TempShouldNotBeNull() {
        TextView textView = (TextView) activity.findViewById(R.id.temp_label);
        assertNotNull("TextView is null", textView);
    }

    @Test
    public void shouldHaveChangeLocationButton() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        Button changeLocation = (Button) activity.findViewById(R.id.button2);
        assertNotNull(changeLocation);
        assertEquals(View.VISIBLE, changeLocation.getVisibility());
    }

    @Test
    public void shouldHaveIcon() throws Exception {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        ImageView icon = (ImageView) activity.findViewById(R.id.icon_imageView);
        assertNotNull(icon);
        assertEquals(View.VISIBLE, icon.getVisibility());
    }

    @Test
    public void editTextForLocationShouldBeNullUponStartup() {
        String text = editLoc.getText().toString();
        assertThat(text, equalTo(""));
    }






}
