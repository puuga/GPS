package space.siwawesw.app.gps.util

import android.app.Activity
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import java.lang.Exception

class LocationUtil private constructor() {

    private object Holder { val INSTANCE = LocationUtil() }

    companion object {
        val instance: LocationUtil by lazy { Holder.INSTANCE }
    }

    var a = 0

    fun initService(activity: Activity, callback: LocationUtilCallback) {
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(createLocationRequest())

        val client: SettingsClient = LocationServices.getSettingsClient(activity)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnCompleteListener{ locationSettingsResponse ->  callback.onLocationSettingSuccess(locationSettingsResponse) }
        task.addOnFailureListener { exception -> callback.onLocationSettingFail(exception) }
    }

    private fun createLocationRequest(): LocationRequest {
        return LocationRequest().apply {
            interval = 5000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    interface LocationUtilCallback {
        fun onLocationSettingSuccess(locationSettingsResponse: Task<LocationSettingsResponse>)
        fun onLocationSettingFail(exception: Exception)
    }
}