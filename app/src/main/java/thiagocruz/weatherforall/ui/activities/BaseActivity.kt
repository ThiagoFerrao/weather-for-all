package thiagocruz.weatherforall.ui.activities

import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.google.android.gms.common.api.Status
import thiagocruz.weatherforall.BuildConfig
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.R
import thiagocruz.weatherforall.entities.CityForecast
import thiagocruz.weatherforall.presenters.PresenterImpl
import thiagocruz.weatherforall.presenters.PresenterInterface
import thiagocruz.weatherforall.views.ViewInterface

abstract class BaseActivity : AppCompatActivity(), ViewInterface {

    var mPresenter: PresenterInterface? = null
    var mList: List<CityForecast>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getActivityLayoutResId())
        setSupportActionBar(getActivityToolbar())

        mPresenter = PresenterImpl()
        mPresenter?.attachView(this, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        mPresenter?.handleActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mPresenter?.handleRequestPermissionsResult(requestCode, permissions, grantResults)

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    // MARK: ViewInterface

    override fun setupViewContent() {
        setupContent()
    }

    override fun showLocationPermissionRequestDialog(permission: String) {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_location_request_title))
                .setMessage(getString(R.string.dialog_location_request_message))
                .setNegativeButton(getString(R.string.dialog_negative_button)) { _, _ -> print("[MapActivity] User Declined The Location Permission Request") }
                .setPositiveButton(getString(R.string.dialog_positive_button)) { _, _ ->
                    mPresenter?.locationPermissionRequestAccepted(permission)
                }
                .setCancelable(false)
                .show()
    }

    override fun showLocationPermissionDeniedPermanentlyDialog() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_location_request_title))
                .setMessage(getString(R.string.dialog_location_denied_request_message))
                .setNegativeButton(getString(R.string.dialog_negative_button)) { _, _ -> print("[MapActivity] User Declined The Location Permission Request") }
                .setPositiveButton(getString(R.string.dialog_positive_button)) { _, _ ->
                    mPresenter?.locationPermissionRequestOpeningSettingsAccepted()
                }
                .setCancelable(false)
                .show()
    }

    override fun showLocationToBeTurnedOnRequest(status: Status) {
        try {
            status.startResolutionForResult(this, Constant.ActivityResultRequestCode.GEOLOCATION)
        } catch (e: IntentSender.SendIntentException) {
            print("[MapActivity] Error during showLocationToBeTurnedOnRequest - ${e.localizedMessage}")
        }
    }

    override fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_error_title))
                .setMessage(message)
                .setPositiveButton(getString(R.string.dialog_positive_button)) { _, _ -> }
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

    override fun loadCityForecastList(result: List<CityForecast>) {
        mList = result
        cityForecastListWasUpdated()
    }

    override fun presentScreen() {
        presentNextScreen()
    }

    override fun showLoading() {
        showLoadingScreen()
    }

    override fun hideLoading() {
        hideLoadingScreen()
    }


    // MARK: Abstract Methods

    abstract fun getActivityLayoutResId(): Int
    abstract fun getActivityToolbar(): Toolbar
    abstract fun setupContent()
    abstract fun cityForecastListWasUpdated()
    abstract fun presentNextScreen()
    abstract fun showLoadingScreen()
    abstract fun hideLoadingScreen()
}
