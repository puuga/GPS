package space.siwawesw.app.gps.activity

import android.Manifest
import android.content.IntentSender
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task

import kotlinx.android.synthetic.main.activity_main.*
import space.siwawesw.app.gps.R
import space.siwawesw.app.gps.util.LocationUtil
import java.lang.Exception
import com.google.android.gms.location.LocationServices
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import space.siwawesw.app.gps.fragment.MainActivityFragment


class MainActivity : AppCompatActivity(), LocationUtil.LocationUtilCallback, LocationUtil.LocationReceiveCallback {

    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_LOCATION = 1011
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()

            requestLocationUpdate()


        }

        LocationUtil.instance.initService(this, this, this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()

        if (LocationUtil.instance.isRunning)
            LocationUtil.instance.stopLocationUpdates()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "We can now safely use the API we requested access to")
                // We can now safely use the API we requested access to
                LocationUtil.instance.startLocationUpdates()
            } else {
                // Permission was denied or request was cancelled
                Log.d(TAG, "Permission was denied or request was cancelled")
            }
        }
    }

    override fun onLocationSettingSuccess(locationSettingsResponse: Task<LocationSettingsResponse>) {
        Log.d(TAG, "onLocationSettingSuccess")
    }

    override fun onLocationSettingFail(exception: Exception) {
        if (exception is ResolvableApiException) {
            // Location settings are not satisfied, but this can be fixed
            // by showing the user a dialog.
            try {
                // Show the dialog by calling startResolutionForResult(),
                // and check the result in onActivityResult().
//                exception.startResolutionForResult(this@MainActivity, REQUEST_CHECK_SETTINGS)
            } catch (sendEx: IntentSender.SendIntentException) {
                // Ignore the error.
            }
        }
    }

    override fun onLocationResult(locationResult: LocationResult) {
        Log.d(TAG, "onLocationResult")
        val fm: MainActivityFragment = supportFragmentManager.findFragmentById(R.id.fragment) as MainActivityFragment
        for (location in locationResult.locations) {
            fm.updateLocationWith(location)
            
            var message = "\n fm.location.latitude: ${fm.mLocation.latitude.get()}"
            message += "\n fm.location.longitude: ${fm.mLocation.longitude.get()}"
            message += "\n fm.location.accuracy: ${fm.mLocation.accuracy.get()}"
            Log.d(TAG, message)
        }
    }

    private fun requestLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION)
        } else {
            // permission has been granted, continue as usual
            LocationUtil.instance.startLocationUpdates()
        }
    }
}
