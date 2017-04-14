package com.example.aman.mobileweathercompanion;

/**
 * Created by colep on 4/10/2017.
 */

public class LongLat {
    private double Longitude =0.0;
    private double Latitude = 0.0;

    public LongLat(){
    }

    public LongLat(double longitude, double latitude) {
        Longitude = longitude;
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }


}
