package space.siwawesw.app.gps.util

import com.google.android.gms.location.LocationRequest

class LocationUtil {

    companion object {
        lateinit var instance: LocationUtil
            private set
    }

    fun createLocationRequest() {
        val locationRequest = LocationRequest().apply {
            interval = 5000
            fastestInterval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
}