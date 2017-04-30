package com.example.aman.mobileweathercompanion.weather;

import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.aman.mobileweathercompanion.R;

import java.util.Date;

/**
 * Created by Aman on 3/25/17.
 */

public class CurrentWeather {
    private String mIcon;
    private long mTime;
    private double mTemp;
    private double mHumidity;
    private double mPrecipChance;
    private String mSummary;
    private String mTimeZone;

    public String getmTimeZone() {
        return mTimeZone;
    }

    public void setmTimeZone(String mTimeZone) {
        this.mTimeZone = mTimeZone;
    }

    public String getmIcon() {
        return mIcon;
    }

    public int getIconId() {
        int iconId = R.drawable.ic_day;

        if(mIcon.equals("clear-day")){
            iconId = R.drawable.ic_day;
        }
        else if(mIcon .equals("clear-night")){
            iconId = R.drawable.ic_night;
        }

        else if (mIcon.equals("rain")) {
            iconId = R.drawable.ic_rainy;
        }
        else if (mIcon.equals("snow")) {
            iconId = R.drawable.ic_snowy;
        }
        else if (mIcon.equals("sleet")) {
            iconId = R.drawable.ic_snowy;
        }
        else if (mIcon.equals("wind")) {
            iconId = R.drawable.ic_day;
        }
        else if (mIcon.equals("fog")) {
            iconId = R.drawable.ic_cloudy;
        }
        else if (mIcon.equals("cloudy")) {
            iconId = R.drawable.ic_cloudy;
        }
        else if (mIcon.equals("partly-cloudy-day")) {
            iconId = R.drawable.ic_cloudyday;
        }
        else if (mIcon.equals("partly-cloudy-night")) {
            iconId = R.drawable.ic_cloudynight;
        }

        return iconId;
    }

    public void setmIcon(String mIcon) {
        this.mIcon = mIcon;
    }

    public long getmTime() {
        return mTime;
    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getFormattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getmTimeZone()));
        Date dateTime = new Date(getmTime() * 1000);
        String timeString = formatter.format(dateTime);
        return timeString;
    }

    public int getmTemp() {
        return (int) Math.round(mTemp);
    }

    public void setmTemp(double mTemp) {
        this.mTemp = mTemp;
    }

    public double getmHumidity() {
        return mHumidity;
    }

    public void setmHumidity(double mHumidity) {
        this.mHumidity = mHumidity;
    }

    public double getmPrecipChance() {
        return mPrecipChance;
    }

    public void setmPrecipChance(double mPrecipChance) {
        this.mPrecipChance = mPrecipChance;
    }

    public String getmSummary() {
        return mSummary;
    }

    public void setmSummary(String mSummary) {
        this.mSummary = mSummary;
    }
}
