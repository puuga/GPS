package space.siwawesw.app.gps.fragment

import android.databinding.DataBindingUtil
import android.databinding.ObservableDouble
import android.databinding.ObservableFloat
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import space.siwawesw.app.gps.R
import space.siwawesw.app.gps.databinding.FragmentMainBinding
import space.siwawesw.app.gps.model.Location

/**
 * A placeholder fragment containing a simple view.
 */
class MainActivityFragment : Fragment() {

    lateinit var mLocation: Location
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        mLocation = Location(ObservableDouble(1.0), ObservableDouble(1.0), ObservableFloat(1f))
        binding.location = mLocation

        return binding.root
    }

    fun updateLocationWith(location: android.location.Location) {
        mLocation.updateWith(location)
    }
}
