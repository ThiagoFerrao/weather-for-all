package thiagocruz.weatherforall.ui.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_main.*
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.R
import thiagocruz.weatherforall.managers.TemperatureUnitManager
import java.lang.Exception

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
            mMap?.clear()

            for (cityForecast in it) {
                val cityName = cityForecast.name
                val cityLatLng = cityForecast.coordinate.getCityLatLng()

                val tempUnit = TemperatureUnitManager.getCurrentTemperatureUnit(this)
                val degreesPrefix = if (TemperatureUnitManager.isTemperatureUnitCelsius(tempUnit)) {
                    "°C"
                } else {
                    "°F"
                }
                val cityMinTemp = String.format("Min: %.1f$degreesPrefix", cityForecast.temperature.minimumTemp)
                val cityMaxTemp = String.format("Max: %.1f$degreesPrefix", cityForecast.temperature.maximumTemp)

                val markerOptions = MarkerOptions()
                        .position(cityLatLng)
                        .title(cityName)
                        .snippet("$cityMaxTemp  $cityMinTemp")

                Picasso.get()
                        .load(cityForecast.weather.first().getWeatherImageUrl())
                        .placeholder(R.drawable.logo_splash)
                        .error(R.drawable.logo_splash)
                        .into(object : Target {
                            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                            }

                            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                                mMap?.addMarker(markerOptions)
                            }

                            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                                val weatherIcon = Bitmap.createScaledBitmap(bitmap, 150, 150, false)
                                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(weatherIcon))
                                mMap?.addMarker(markerOptions)
                            }
                        })
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
        mMap?.setOnMarkerClickListener { marker: Marker? ->
            marker?.showInfoWindow()
            true
        }
    }
}
