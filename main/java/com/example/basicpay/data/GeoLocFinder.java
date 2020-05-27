package com.example.basicpay.data;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.basicpay.SelectRecipActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GeoLocFinder {

    private FusedLocationProviderClient mFLClient;
    private static Logger l = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private SelectRecipActivity appl;

    private String lon;
    private String lat;

    public String getLat() {
        return this.lat;
    }

    private void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return this.lon;
    }

    private void setLon(String lon) {
        this.lon = lon;
    }

    public GeoLocFinder(SelectRecipActivity appl) {
        this.appl = appl;
        mFLClient = LocationServices.getFusedLocationProviderClient(appl);
        findLocation();
    }

    private boolean checkPermissions(){
        return (ActivityCompat.checkSelfPermission(appl, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(appl, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);

    }

    private void requestPermissions(){
        int[] permissionResult = new int[1];
        int GEO_PERMISSION_ID = 44;
        ActivityCompat.requestPermissions(
                appl,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                GEO_PERMISSION_ID
        );
        appl.onRequestPermissionsResult(
                GEO_PERMISSION_ID,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                permissionResult
        );
        if (permissionResult[0] == PackageManager.PERMISSION_GRANTED) {
            findLocation();
        }
    }

    private boolean isLocationEnabled(){
        LocationManager locationManager = (LocationManager) appl.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            l.log(Level.INFO, "GPS_PROVIDER **********");
        }
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            setLat(mLastLocation.getLatitude()+"");
            setLon(mLastLocation.getLongitude()+"");
            l.log(Level.INFO, "Callback : Lat : " + (mLastLocation.getLatitude()));
            l.log(Level.INFO, "Callback : Lon : " + (mLastLocation.getLongitude()));
        }
    };

    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){
        l.log(Level.INFO, "reqNewLoc start..........");
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(5000);

        mFLClient = LocationServices.getFusedLocationProviderClient(appl);
        mFLClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }

    @SuppressLint("MissingPermission")
    private void findLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFLClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @SuppressLint("Assert")
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location != null) {
                                    assert false;
                                    l.log(Level.INFO, "Superb : " + (location.getLatitude()));
                                    l.log(Level.INFO, String.valueOf(location.getLongitude()));
                                    setLat(location.getLatitude()+"");
                                    setLon(location.getLongitude()+"");
                                } else {
                                    requestNewLocationData();
                                    l.log(Level.INFO, "Else req to new loc..........");
                                }
                            }
                        }
                );
            }
            else {
                Toast.makeText(appl, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                appl.startActivity(intent);
            }
        }
        else {
            requestPermissions();
        }
    }
}
