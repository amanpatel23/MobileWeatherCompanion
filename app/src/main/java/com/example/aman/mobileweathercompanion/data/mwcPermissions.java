package com.example.aman.mobileweathercompanion.data;

/**
 * Created by colep on 5/13/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.FusedLocationProviderApi;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;


public class mwcPermissions extends AppCompatActivity implements LocationListener {
    private static final int MY_PERMISSIONS_REQUEST = 1;
    private static final int FINE_LOCATION_PERMISSION = 101;
    private static final int COARSE_LOCATION_PERMISSION = 102;
    private final Context mContext;
    private boolean permissionGrant = false;

    public mwcPermissions(Context mContext) {
        this.mContext = mContext.getApplicationContext();
    }

    public Context getmContext() {
        return mContext;
    }

    public boolean isPermissionGrant() {
        return permissionGrant;
    }

    public void setPermissionGrant(boolean permissionGrant) {
        this.permissionGrant = permissionGrant;
    }


    public void havePermission (){
        int permissionCheck = ContextCompat.checkSelfPermission((Activity)mContext, Manifest.permission.ACCESS_COARSE_LOCATION);
        // Here, thisActivity is the current activity

        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions((Activity) mContext,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST);
            }
        }
    }

   /* public void havePermission2 (){
        int permissionCheck = ContextCompat.checkSelfPermission((Activity)mContext, Manifest.permission.LOCATION_HARDWARE);
        // Here, thisActivity is the current activity

        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.LOCATION_HARDWARE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                    Manifest.permission.LOCATION_HARDWARE)) {

            } else {

                ActivityCompat.requestPermissions((Activity) mContext,
                        new String[]{Manifest.permission.LOCATION_HARDWARE},
                        MY_PERMISSIONS_REQUEST);
            }
        }
    } */

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case FINE_LOCATION_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGrant = true;
                } else {
                    permissionGrant = false;
                    Toast.makeText(getApplicationContext(), "The button you clicked requires location services", Toast.LENGTH_SHORT).show();
                }
                break;
            case COARSE_LOCATION_PERMISSION:
                    // do something for coarse location
                    break;

        }
    }

    public void havePermission3(){
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},FINE_LOCATION_PERMISSION);

            return;
        }



    }



    @Override
    public void onLocationChanged(Location location) {

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

    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);
    }

}
