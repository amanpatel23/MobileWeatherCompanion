package com.example.aman.mobileweathercompanion.data;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LongLat)) return false;

        LongLat longLat = (LongLat) o;

        if (Double.compare(longLat.getLongitude(), getLongitude()) != 0) return false;
        return Double.compare(longLat.getLatitude(), getLatitude()) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getLongitude());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLatitude());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
