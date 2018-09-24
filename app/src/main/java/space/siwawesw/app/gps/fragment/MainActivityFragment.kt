package space.siwawesw.app.gps.fragment

import android.databinding.DataBindingUtil
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

    lateinit var location: Location

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        location = Location(1.0, 1.0, 1f)
        binding.location = location

        return binding.root
    }
}
