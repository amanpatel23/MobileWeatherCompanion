package com.example.aman.mobileweathercompanion.weather;

import com.example.aman.mobileweathercompanion.R;

/**
 * Created by Aman on 4/15/17.
 */

public class Forecast {

    private CurrentWeather mCurrentWeather;
    private Day[] mDailyForecast;

    public CurrentWeather getCurrentWeather() {
        return mCurrentWeather;
    }

    public void setCurrentWeather(CurrentWeather current) {
        mCurrentWeather = current;
    }


    public Day[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(Day[] dailyForecast) {
        mDailyForecast = dailyForecast;
    }



    public static int getIconId(String iconString){
        int iconId = R.drawable.ic_day;

        if(iconString.equals("clear-day")){
            iconId = R.drawable.ic_day;
        }
        else if(iconString.equals("clear-night")){
            iconId = R.drawable.ic_night;
        }

        else if (iconString.equals("rain")) {
            iconId = R.drawable.ic_rainy;
        }
        else if (iconString.equals("snow")) {
            iconId = R.drawable.ic_snowy;
        }
        else if (iconString.equals("sleet")) {
            iconId = R.drawable.ic_snowy;
        }
        else if (iconString.equals("wind")) {
            iconId = R.drawable.ic_day;
        }
        else if (iconString.equals("fog")) {
            iconId = R.drawable.ic_cloudy;
        }
        else if (iconString.equals("cloudy")) {
            iconId = R.drawable.ic_cloudy;
        }
        else if (iconString.equals("partly-cloudy-day")) {
            iconId = R.drawable.ic_cloudyday;
        }
        else if (iconString.equals("partly-cloudy-night")) {
            iconId = R.drawable.ic_cloudynight;
        }
        return iconId;

    }
}
