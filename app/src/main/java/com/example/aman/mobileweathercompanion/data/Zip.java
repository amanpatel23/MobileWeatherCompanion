package com.example.aman.mobileweathercompanion.data;

/**
 * Created by colep on 4/24/2017.
 */

public class Zip {
    private String ZipString ="";
    private int ZipInt = 0;

    public Zip(){
    }

    public Zip(String zip) {
        try{
            ZipInt = Integer.parseInt(zip);
        }
        catch(Exception e){

        }
        ZipString = zip;
    }

    public int getZipInt (){
        return ZipInt;
    }

    public void setZipInt (int zip) {
        ZipInt = zip;
    }

    public String getZipString() {
        return ZipString;
    }

    public void setZipString(String latitude) {
        ZipString = latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zip)) return false;

        Zip zip = (Zip) o;

        if (getZipInt() != zip.getZipInt()) return false;
        return getZipString().equals(zip.getZipString());

    }

    @Override
    public int hashCode() {
        int result = getZipString().hashCode();
        result = 31 * result + getZipInt();
        return result;
    }
}
