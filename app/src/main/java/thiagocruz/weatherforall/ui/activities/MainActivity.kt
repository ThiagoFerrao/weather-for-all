package thiagocruz.weatherforall.ui.activities

import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.api.Status
import kotlinx.android.synthetic.main.activity_main.*
import thiagocruz.weatherforall.BuildConfig
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.R
import thiagocruz.weatherforall.presenters.MainPresenter
import thiagocruz.weatherforall.presenters.MainPresenterImpl
import thiagocruz.weatherforall.views.MainView

class MainActivity : AppCompatActivity(), MainView {

    private var mPresenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { _ -> }

        mPresenter = MainPresenterImpl()
        mPresenter?.attachView(this, this)
        mPresenter?.getUserLocation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mPresenter?.handleActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mPresenter?.handleRequestPermissionsResult(requestCode, permissions, grantResults)

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    // MARK: MainView

    override fun showLocationPermissionRequestDialog(permission: String) {
        AlertDialog.Builder(this)
                .setTitle("")
                .setMessage("")
                .setNegativeButton("") { _, _ -> print("[MainActivity] User Declined The Location Permission Request") }
                .setPositiveButton("") { _, _ ->
                    mPresenter?.locationPermissionRequestAccepted(permission)
                }
                .setCancelable(false)
                .show()
    }

    override fun showLocationPermissionDeniedPermanentlyDialog() {
        AlertDialog.Builder(this)
                .setTitle("")
                .setMessage("")
                .setNegativeButton("") { _, _ -> print("[MainActivity] User Declined The Location Permission Request") }
                .setPositiveButton("") { _, _ ->
                    mPresenter?.locationPermissionRequestOpeningSettingsAccepted()
                }
                .setCancelable(false)
                .show()
    }

    override fun showLocationToBeTurnedOnRequest(status: Status) {
        try {
            status.startResolutionForResult(this, Constant.ActivityResultRequestCode.GEOLOCATION)
        } catch (e: IntentSender.SendIntentException) {
            print("[MainActivity] Error during showLocationToBeTurnedOnRequest - ${e.localizedMessage}")
        }
    }

    override fun showLocationRequestFailedDialog() {
        AlertDialog.Builder(this)
                .setTitle("")
                .setMessage("")
                .setPositiveButton("") { _, _ -> }
                .setCancelable(false)
                .show()
    }

    override fun requestPermission(permission: String) {
        requestPermissions(arrayOf(permission), Constant.PermissionRequestCode.GEOLOCATION)
    }

    override fun showAppSettingsScreen() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + BuildConfig.APPLICATION_ID))
        startActivity(intent)
    }
}
