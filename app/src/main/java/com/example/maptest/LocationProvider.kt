package com.example.maptest

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import android.widget.Toast

class LocationProvider(context: Context) {
    var location: Location?
    var locationManager: LocationManager
    var canGetLocation: Boolean

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        var isNetworkEnabled = false
        var isGPSEnabled = false
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isNetworkEnabled && !isGPSEnabled) {
            canGetLocation = false
            location = null
            return
        }
        var provider: String? = null
        if (isNetworkEnabled) provider = LocationManager.NETWORK_PROVIDER
        if (isGPSEnabled) provider = LocationManager.GPS_PROVIDER
        location = locationManager.getLastKnownLocation(provider)
        if (location == null) {
            location = bestLastknownLocation
        }
    }
    @get:SuppressLint("MissingPermission")
    val bestLastknownLocation: Location?
        get() {
            val providers = locationManager.getProviders(false)
            for (provider in providers) {
                val temp = locationManager.getLastKnownLocation(provider)
                Log.d("lol","$location.longitude.toString() +smsm + $location.longitude.toString()" )
                if (location == null) {
                    location = temp
                    continue
                }
                if (temp != null) {
                    if (temp.accuracy > location!!.accuracy) {
                        location = temp
                    }

                }
            }
            return location
        }

    @SuppressLint("MissingPermission")
    fun trackLocation(locationListener: LocationListener?) {
        var isNetworkEnabled = false
        var isGPSEnabled = false
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!isNetworkEnabled && !isGPSEnabled) {
            canGetLocation = false
            location = null
            return
        }
        var provider: String? = null
        if (isNetworkEnabled) provider = LocationManager.NETWORK_PROVIDER
        if (isGPSEnabled) provider = LocationManager.GPS_PROVIDER
        locationManager.requestLocationUpdates(
            provider, MIN_TIME_BETWEEN_UPDATES,
            MIN_DISTANCE_BETWEEN_UPDATES, locationListener
        )
    }

    companion object {
        const val MIN_TIME_BETWEEN_UPDATES = 5 * 1000.toLong()
        const val MIN_DISTANCE_BETWEEN_UPDATES = 20.0f
    }

    init {
        locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        canGetLocation = false
        location = null
        getLocation()
    }

}