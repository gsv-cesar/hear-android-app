package com.mpes.hear.location

import android.app.Activity
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import com.google.android.gms.location.*
import java.io.IOException
import java.util.*
import android.content.Context.LOCATION_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.location.LocationManager
import android.widget.Toast


class FusedLocation(activity: Activity) {
    private val activity = activity
    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

    fun isActive() : Boolean {

        val location = activity.getSystemService(LOCATION_SERVICE) as LocationManager?

        if (!location?.isProviderEnabled(LocationManager.GPS_PROVIDER)!!)
            return false

        return true
    }

    init {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    try {
                        val geo = Geocoder(activity.baseContext, Locale.getDefault())
                        val addresses = geo.getFromLocation(location.latitude, location.longitude, 1)

                        Log.d("Jholl", addresses.toString())
                    } catch (ioException: IOException) {
                        Log.d("Jholl", "io", ioException)
                    } catch (illegalArgumentException: IllegalArgumentException) {
                        Log.d("Jholl", "erro long/lat. Latitude = $location.latitude , " +
                                "Longitude =  $location.longitude", illegalArgumentException)
                    }
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(LocationRequest(), locationCallback, null )
    }

    fun getLocation(listener: (latitude: Double?, longitude: Double?, endereco: Address?) -> Unit) {
        fusedLocationClient
            .lastLocation
            .addOnSuccessListener { location : Location? ->
                Log.d("Jholl", location.toString())
                if (location != null) {
                    try {
                        val geo = Geocoder(activity.baseContext, Locale.getDefault())
                        val addresses = geo.getFromLocation(location.latitude, location.longitude, 1)

                        Log.d("Jholl", addresses.toString())

                        listener(location.latitude, location.longitude, addresses[0])
                    } catch (ioException: IOException) {
                        Log.d("Jholl", "io", ioException)

                        listener(null, null, null)
                    } catch (illegalArgumentException: IllegalArgumentException) {
                        Log.d("Jholl", "erro long/lat. Latitude = $location.latitude , " +
                                "Longitude =  $location.longitude", illegalArgumentException)

                        listener(null, null, null)
                    }

                } else {
                    listener(null, null, null)
                }
            }
    }
}