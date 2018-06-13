package thiagocruz.weatherforall.ui.activities

import android.location.Location
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.api.Status
import kotlinx.android.synthetic.main.activity_main.*
import thiagocruz.weatherforall.R
import thiagocruz.weatherforall.managers.GeolocationManager
import thiagocruz.weatherforall.managers.GeolocationManagerInterface

class MainActivity : AppCompatActivity(), GeolocationManagerInterface.Listener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        GeolocationManager.getUserLocation(this, this)
    }

    override fun onPermissionNeedToBeApproved(permission: String) {
        AlertDialog.Builder(this).setMessage("onPermissionNeedToBeApproved").show()
    }

    override fun onPermissionDeniedPermanently() {
        AlertDialog.Builder(this).setMessage("onPermissionDeniedPermanently").show()
    }

    override fun onLocationNeedToBeTurnedOn(status: Status) {
        AlertDialog.Builder(this).setMessage("onLocationNeedToBeTurnedOn").show()
    }

    override fun onUserLocationSuccessfullyRetrieved(location: Location) {
        AlertDialog.Builder(this).setMessage("onUserLocationSuccessfullyRetrieved").show()
    }

    override fun onUserLocationFailedToBeRetrieved() {
        AlertDialog.Builder(this).setMessage("onUserLocationFailedToBeRetrieved").show()
    }
}
