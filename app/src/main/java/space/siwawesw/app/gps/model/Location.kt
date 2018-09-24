package space.siwawesw.app.gps.model

import android.location.Location

data class Location (var latitude: Double,
                     var longitude: Double,
                     var accuracy: Float) {

    fun updateWith(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
        accuracy = location.accuracy
    }
}
