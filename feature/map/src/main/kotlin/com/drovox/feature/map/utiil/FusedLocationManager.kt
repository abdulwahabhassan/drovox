package com.drovox.feature.map.utiil

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import timber.log.Timber

/**
 * This class is responsible for managing location tracking using [LocationManager] and
 * [FusedLocationProviderClient]. On initializing an instance of this class,
 * call [startLocationUpdates] in 'onStart' or 'onResume' of an activity's lifecycle
 * to trigger location (lat, long) updates. Call [stopLocationUpdates]
 * in 'onPause' or 'onStop' of an activity's lifecycle to stop location update.
 */
class FusedLocationManager(private val context: Context) {

    private var pairOfClientCallBack: Pair<FusedLocationProviderClient, LocationCallback>? = null

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(
        onLocationUpdates: ((longitude: Double, latitude: Double) -> Unit),
    ) {
        val locationManager by lazy { context.getSystemService(Context.LOCATION_SERVICE) as LocationManager }

        val fusedLocationProviderClient by lazy {
            LocationServices.getFusedLocationProviderClient(
                context
            )
        }

        val locationCallback: LocationCallback by lazy {
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    val location = locationResult.lastLocation
                    location?.let {
                        onLocationUpdates(it.longitude, it.latitude)
                    }
                }
            }
        }

        val locationRequest by lazy {
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setGranularity(Granularity.GRANULARITY_FINE)
                .setMaxUpdateDelayMillis(2000)
                .build();
        }

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        ) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
            )
            pairOfClientCallBack = Pair(fusedLocationProviderClient, locationCallback)
        } else {
            context.startActivity(
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    fun stopLocationUpdates() {
        pairOfClientCallBack?.let { it.first.removeLocationUpdates(it.second) }
    }

}