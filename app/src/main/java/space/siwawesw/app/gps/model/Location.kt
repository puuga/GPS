package space.siwawesw.app.gps.model

import android.databinding.ObservableDouble
import android.databinding.ObservableFloat
import android.location.Location

data class Location (var latitude: ObservableDouble,
                     var longitude: ObservableDouble,
                     var accuracy: ObservableFloat) {

    fun updateWith(location: Location) {
        latitude.set(location.latitude)
        longitude.set(location.longitude)
        accuracy.set(location.accuracy)
    }
}
