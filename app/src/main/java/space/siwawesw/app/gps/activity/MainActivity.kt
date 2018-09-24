package space.siwawesw.app.gps.activity

import android.content.IntentSender
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.Task

import kotlinx.android.synthetic.main.activity_main.*
import space.siwawesw.app.gps.R
import space.siwawesw.app.gps.util.LocationUtil
import java.lang.Exception

class MainActivity : AppCompatActivity(), LocationUtil.LocationUtilCallback {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        LocationUtil.instance.initService(this, this)
        Log.d(TAG, "LocationUtil.instance.a: ${LocationUtil.instance.a}")
        LocationUtil.instance.a++
        Log.d(TAG, "LocationUtil.instance.a: ${LocationUtil.instance.a}")
        LocationUtil.instance.a++
        Log.d(TAG, "LocationUtil.instance.a: ${LocationUtil.instance.a}")
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
    }

    override fun onLocationSettingSuccess(locationSettingsResponse: Task<LocationSettingsResponse>) {
        Log.d(TAG, "onLocationSettingSuccess")
    }

    override fun onLocationSettingFail(exception: Exception) {
        if (exception is ResolvableApiException){
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
}
