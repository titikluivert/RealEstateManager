package com.openclassrooms.realestatemanager.model;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.openclassrooms.realestatemanager.R;


public class CurrentLocation {

    private double latitude;
    private double longitude;


    public CurrentLocation() {
    }

    public CurrentLocation(double latitude, double longitude) {

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void saveLatLng(Context context, float latitude, float longitude) {


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(context.getString(R.string.save_lat__key), latitude);
        editor.putFloat(context.getString(R.string.save_Long__key), longitude);
        editor.apply();
    }


    public SaveCurrentLocation getSaveLatLng(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return new SaveCurrentLocation(
                sharedPreferences.getFloat(context.getString(R.string.save_lat__key), 0),
                sharedPreferences.getFloat(context.getString(R.string.save_Long__key), 0)

        );

    }

}