package com.example.aman.mobileweathercompanion.data;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.aman.mobileweathercompanion.R;


/**
 * Created by colep on 5/7/2017.
 */

public class CurrentLocation extends Service implements LocationListener {
    private final Context mContext;
    boolean checkGPS = false;
    boolean checkNetwork = false;
    boolean canGetLocation = false;
    Location loc;
    double latitude;
    double longitude;
    protected LocationManager lm;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    public CurrentLocation(Context mContext) {
        this.mContext = mContext;
        getLocation();
    }

    public void getLocation(){
        lm = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        checkGPS = lm.isProviderEnabled(lm.GPS_PROVIDER);
        checkNetwork = lm.isProviderEnabled(lm.NETWORK_PROVIDER);
        if (!checkGPS && !checkNetwork) {
            Toast.makeText(mContext, "GPS and/or Network isn't on!", Toast.LENGTH_SHORT).show();
        } else {
            this.canGetLocation = true;
            if (checkNetwork) {
                Toast.makeText(mContext, "Network", Toast.LENGTH_SHORT).show();

                try {
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (lm != null) {
                        loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }
                    if (loc != null) {
                        latitude = loc.getLatitude();
                        longitude = loc.getLongitude();
                        System.out.println(latitude +"............."+ longitude);
                    }
                }
                catch(SecurityException e){

                }
            }
        }
        if (checkGPS) {
            Toast.makeText(mContext,"GPS",Toast.LENGTH_SHORT).show();
            if (loc == null) {
                try {
                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (lm != null) {
                        loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null) {
                            latitude = loc.getLatitude();
                            longitude = loc.getLongitude();
                            System.out.println(latitude +"........2....."+ longitude);
                        }
                    }
                } catch (SecurityException e) {

                }
            }
        }


    }

    public Context getmContext() {
        return mContext;
    }

    public boolean isCheckGPS() {
        return checkGPS;
    }

    public void setCheckGPS(boolean checkGPS) {
        this.checkGPS = checkGPS;
    }

    public boolean isCheckNetwork() {
        return checkNetwork;
    }

    public void setCheckNetwork(boolean checkNetwork) {
        this.checkNetwork = checkNetwork;
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }

    public void setCanGetLocation(boolean canGetLocation) {
        this.canGetLocation = canGetLocation;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LocationManager getLocationManager() {
        return lm;
    }

    public void setLocationManager(LocationManager locationManager) {
        this.lm = locationManager;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location loc2) {
        if (loc2 != null) {
            loc = loc2;

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    public void isGPSon(){
        AlertDialog.Builder myAlert = new AlertDialog.Builder(getmContext());
        myAlert.setMessage("Do you want to turn your gps on?");
        myAlert.setTitle("GPS on/off");
        myAlert.setPositiveButton("Yeah",  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            mContext.startActivity(intent);
        }});
        myAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        myAlert.show();
    }
    public void stop(){
        if (lm != null) {

            lm.removeUpdates(CurrentLocation.this);
        }
    }
}
