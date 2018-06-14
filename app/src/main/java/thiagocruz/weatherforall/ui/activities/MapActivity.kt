package thiagocruz.weatherforall.ui.activities

import android.content.Context
import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.R
import thiagocruz.weatherforall.managers.TemperatureUnitManager

class MapActivity : BaseActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
        set(value) {
            field = value
            mPresenter?.initializeWithIntent(intent)
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_map, menu)

        val itemChangeMetrics = menu?.findItem(R.id.action_change_metrics)
        TemperatureUnitManager.setCurrentMenuItemIcon(this, itemChangeMetrics)
        itemChangeMetrics?.setOnMenuItemClickListener { item ->
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


    // MARK: BaseActivity Methods

    override fun getActivityLayoutResId(): Int {
        return R.layout.activity_map
    }

    override fun getActivityToolbar(): Toolbar {
        return toolbar
    }

    override fun setupContent() {
        floatingButtonGetLocation.setOnClickListener { _ -> mPresenter?.getUserLocation() }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun cityForecastListWasUpdated() {
        val preferences = this.getSharedPreferences(Constant.SharedPreferences.DEFAULT, Context.MODE_PRIVATE)
        val userLatitude = preferences.getString(Constant.SharedPreferences.KEY_USER_LOCATION_LATITUDE, null)?.toDouble()
        val userLongitude = preferences.getString(Constant.SharedPreferences.KEY_USER_LOCATION_LONGITUDE, null)?.toDouble()

        if (userLatitude != null && userLongitude != null)
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(userLatitude, userLongitude), Constant.Map.DEFAULT_ZOOM))

        mList?.let {
            for (cityForecast in it) {
                val cityName = cityForecast.name
                val cityLatLng = cityForecast.coordinate.getCityLatLng()

                mMap?.addMarker(MarkerOptions().position(cityLatLng).title(cityName))
            }
        }
    }

    override fun presentNextScreen() {
        val intent = Intent(this, MainActivity::class.java)
        mList?.let { intent.putParcelableArrayListExtra(Constant.IntentExtra.CITY_FORECAST_LIST, ArrayList(it)) }
        startActivity(intent)
        finish()
    }


    // MARK: OnMapReadyCallback

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.uiSettings?.isMapToolbarEnabled = false
    }
}
