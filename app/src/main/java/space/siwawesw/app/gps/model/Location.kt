package space.siwawesw.app.gps.model

import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableFloat
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
