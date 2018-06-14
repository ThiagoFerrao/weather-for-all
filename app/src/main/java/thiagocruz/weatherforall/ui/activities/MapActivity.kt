package thiagocruz.weatherforall.ui.activities

import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import thiagocruz.weatherforall.BuildConfig
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.R
import thiagocruz.weatherforall.entities.CityForecast
import thiagocruz.weatherforall.managers.TemperatureUnitManager
import thiagocruz.weatherforall.presenters.PresenterImpl
import thiagocruz.weatherforall.presenters.PresenterInterface
import thiagocruz.weatherforall.views.ViewInterface

class MapActivity : AppCompatActivity(), ViewInterface, OnMapReadyCallback {

    private var mPresenter: PresenterInterface? = null
    private var mItemChangeMetrics: MenuItem? = null
    private var mList: List<CityForecast>? = null
    private var mMap: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPresenter = PresenterImpl()
        mPresenter?.attachView(this, this)
    }

    override fun onResume() {
        super.onResume()

        TemperatureUnitManager.setCurrentMenuItemIcon(this, mItemChangeMetrics)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_map, menu)

        mItemChangeMetrics = menu?.findItem(R.id.action_change_metrics)
        TemperatureUnitManager.setCurrentMenuItemIcon(this, mItemChangeMetrics)
        mItemChangeMetrics?.setOnMenuItemClickListener { item ->
            mPresenter?.handleChangeMetrics(item)
            true
        }

        val itemChangeToList = menu?.findItem(R.id.action_change_to_list)
        itemChangeToList?.setOnMenuItemClickListener { _ ->
            mPresenter?.handleChangeScreen()
            true
        }

        return true
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
        setContentView(R.layout.activity_map)

        setSupportActionBar(toolbar)

        floatingButtonGetLocation.setOnClickListener { _ -> mPresenter?.getUserLocation() }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

    }

    override fun presentScreen() {
        val intent = Intent(this, MainActivity::class.java)
        mList?.let { intent.putParcelableArrayListExtra(Constant.IntentExtra.CITY_FORECAST_LIST, ArrayList(it)) }
        startActivity(intent)
    }


    // MARK: OnMapReadyCallback

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap?.uiSettings?.isMapToolbarEnabled = false

        val sydney = LatLng(-34.0, 151.0)
        mMap?.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}
