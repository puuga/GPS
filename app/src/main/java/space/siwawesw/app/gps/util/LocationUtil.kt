package space.siwawesw.app.gps.util

import android.app.Activity
import android.util.Log
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import space.siwawesw.app.gps.activity.MainActivity
import java.lang.Exception

class LocationUtil private constructor() {

    private object Holder { val INSTANCE = LocationUtil() }

    companion object {
        private const val TAG = "LocationUtil"
        val instance: LocationUtil by lazy { Holder.INSTANCE }
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    fun initService(activity: Activity, locationUtilCallback: LocationUtilCallback, locationReceiveCallback: LocationReceiveCallback) {
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(createLocationRequest())

        val client: SettingsClient = LocationServices.getSettingsClient(activity)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnCompleteListener{ locationSettingsResponse ->  locationUtilCallback.onLocationSettingSuccess(locationSettingsResponse) }
        task.addOnFailureListener { exception -> locationUtilCallback.onLocationSettingFail(exception) }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    // Update UI with location data
                    // ...
                    Log.d(TAG, "location: $location")
                }

                locationReceiveCallback.onLocationResult(locationResult)
            }
        }
    }

    private fun createLocationRequest(): LocationRequest {
        return LocationRequest().apply {
            interval = 5000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    fun startLocationUpdates() {
        try {
            fusedLocationClient.requestLocationUpdates(createLocationRequest(), locationCallback, null)
        } catch (exception: SecurityException) {
            throw exception
        }
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    interface LocationUtilCallback {
        fun onLocationSettingSuccess(locationSettingsResponse: Task<LocationSettingsResponse>)
        fun onLocationSettingFail(exception: Exception)
    }

    interface LocationReceiveCallback {
        fun onLocationResult(locationResult: LocationResult)
    }
}