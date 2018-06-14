package thiagocruz.weatherforall.ui.activities

import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.common.api.Status
import thiagocruz.weatherforall.BuildConfig
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.R
import thiagocruz.weatherforall.entities.CityForecast
import thiagocruz.weatherforall.managers.TemperatureUnitManager
import thiagocruz.weatherforall.presenters.Presenter
import thiagocruz.weatherforall.presenters.PresenterImpl
import thiagocruz.weatherforall.ui.adapters.CityForecastAdapter
import thiagocruz.weatherforall.views.View

class MainActivity : AppCompatActivity(), View {

    private var mPresenter: Presenter? = null
    private var mAdapter: CityForecastAdapter? = null
    private var mItemChangeMetrics: MenuItem? = null

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
        menuInflater.inflate(R.menu.menu_main, menu)

        mItemChangeMetrics = menu?.findItem(R.id.action_change_metrics)
        TemperatureUnitManager.setCurrentMenuItemIcon(this, mItemChangeMetrics)
        mItemChangeMetrics?.setOnMenuItemClickListener { item ->
            mPresenter?.handleChangeMetrics(item)
            true
        }

        val itemChangeToMap = menu?.findItem(R.id.action_change_to_map)
        itemChangeToMap?.setOnMenuItemClickListener { _ ->
            mPresenter?.handleChangeToMap()
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


    // MARK: View

    override fun setupViewContent() {
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        floatingButtonGetLocation.setOnClickListener { _ -> mPresenter?.getUserLocation() }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()

        mPresenter?.getUserLocation()
    }

    override fun showLocationPermissionRequestDialog(permission: String) {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_location_request_title))
                .setMessage(getString(R.string.dialog_location_request_message))
                .setNegativeButton(getString(R.string.dialog_negative_button)) { _, _ -> print("[MainActivity] User Declined The Location Permission Request") }
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
                .setNegativeButton(getString(R.string.dialog_negative_button)) { _, _ -> print("[MainActivity] User Declined The Location Permission Request") }
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
            print("[MainActivity] Error during showLocationToBeTurnedOnRequest - ${e.localizedMessage}")
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
        if (recyclerView.adapter != null) {
            mAdapter?.setCityForecastList(result)
            mAdapter?.notifyDataSetChanged()
            return
        }

        mAdapter = CityForecastAdapter(this)
        mAdapter?.setCityForecastList(result)
        recyclerView.adapter = mAdapter
    }

    override fun presentMap() {
        val intent = Intent(this, MapActivity::class.java)
        mAdapter?.mList?.let { intent.putParcelableArrayListExtra(Constant.IntentExtra.CITY_FORECAST_LIST, ArrayList(it)) }
        startActivity(intent)
    }
}
