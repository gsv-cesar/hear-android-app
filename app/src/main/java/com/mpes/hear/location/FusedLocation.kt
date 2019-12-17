package com.mpes.hear.location

import android.app.Activity
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class FusedLocation(activity: Activity) {
    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

    fun getLocation() {
        fusedLocationClient
            .lastLocation
            .addOnSuccessListener { location : Location? ->
                Log.d("Jholl", location.toString())
            }
            .addOnCompleteListener {
                Log.d("Jholl", it.toString())
            }
    }
}