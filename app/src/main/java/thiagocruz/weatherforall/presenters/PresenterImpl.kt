package thiagocruz.weatherforall.presenters

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.view.MenuItem
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.R
import thiagocruz.weatherforall.entities.CityForecast
import thiagocruz.weatherforall.interactors.InteractorInterface
import thiagocruz.weatherforall.interactors.InteractorImpl
import thiagocruz.weatherforall.managers.GeoLocationManager
import thiagocruz.weatherforall.managers.GeolocationManagerInterface
import thiagocruz.weatherforall.managers.TemperatureUnitManager
import thiagocruz.weatherforall.views.ViewInterface

class PresenterImpl : PresenterInterface, GeolocationManagerInterface.Listener, InteractorInterface.WeatherForecastListener {

    private var mView: ViewInterface? = null
    private var mActivity: Activity? = null
    private var mInteractor: InteractorInterface? = null


    // MARK: PresenterInterface

    override fun attachView(view: ViewInterface, activity: Activity) {
        mView = view
        mActivity = activity
        mInteractor = InteractorImpl()

        mView?.setupViewContent()
    }

    override fun initializeWithIntent(intent: Intent?) {
        val cityForecastList = intent?.getParcelableArrayListExtra<CityForecast>(Constant.IntentExtra.CITY_FORECAST_LIST)

        if (cityForecastList != null) {
            mView?.loadCityForecastList(cityForecastList)
            return
        }

        getUserLocation()
    }

    override fun mapCameraPositionUpdated(newCameraLatLng: LatLng?) {
        if (newCameraLatLng == null) {
            return
        }

        val newLocation = Location("")
        newLocation.latitude = newCameraLatLng.latitude
        newLocation.longitude = newCameraLatLng.longitude

        mActivity?.let { mInteractor?.findWeatherForecast(it, newLocation, this) }
    }

    override fun getUserLocation() {
        mActivity?.let { GeoLocationManager.getUserLocation(it, this) }
    }

    override fun locationPermissionRequestOpeningSettingsAccepted() {
        mView?.showAppSettingsScreen()
    }

    override fun locationPermissionRequestAccepted(permission: String) {
        mView?.requestPermission(permission)
    }

    override fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Constant.ActivityResultRequestCode.GEOLOCATION -> {
                if (resultCode != RESULT_OK) {
                    return print("[PresenterImpl] User Declined The Location Permission Request")
                }

                mActivity?.let { GeoLocationManager.getUserLocation(it, this) }
            }
        }
    }

    override fun handleRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            Constant.PermissionRequestCode.GEOLOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mActivity?.let { GeoLocationManager.getUserLocation(it, this) }
                    return
                }

                print("[PresenterImpl] User Declined Turning On The Location")
            }
        }
    }

    override fun handleChangeMetrics(item: MenuItem?) {
        TemperatureUnitManager.updateTemperatureUnit(mActivity, item)

        val preferences = mActivity?.getSharedPreferences(Constant.SharedPreferences.DEFAULT, Context.MODE_PRIVATE)
                ?: return
        val userLatitude = preferences.getString(Constant.SharedPreferences.KEY_USER_LOCATION_LATITUDE, null)?.toDouble()
                ?: return
        val userLongitude = preferences.getString(Constant.SharedPreferences.KEY_USER_LOCATION_LONGITUDE, null)?.toDouble()
                ?: return

        val userLocation = Location("")
        userLocation.latitude = userLatitude
        userLocation.longitude = userLongitude

        mActivity?.let { mInteractor?.findWeatherForecast(it, userLocation, this) }
    }

    override fun handleChangeScreen() {
        mView?.presentScreen()
    }


    // MARK: GeolocationManagerInterface.Listener

    override fun onPermissionNeedToBeApproved(permission: String) {
        mView?.showLocationPermissionRequestDialog(permission)
    }

    override fun onPermissionDeniedPermanently() {
        mView?.showLocationPermissionDeniedPermanentlyDialog()
    }

    override fun onLocationNeedToBeTurnedOn(status: Status) {
        mView?.showLocationToBeTurnedOnRequest(status)
    }

    override fun onUserLocationSuccessfullyRetrieved(location: Location) {
        val editor = mActivity?.getSharedPreferences(Constant.SharedPreferences.DEFAULT, Context.MODE_PRIVATE)?.edit()
        editor?.putString(Constant.SharedPreferences.KEY_USER_LOCATION_LATITUDE, location.latitude.toString())
        editor?.putString(Constant.SharedPreferences.KEY_USER_LOCATION_LONGITUDE, location.longitude.toString())
        editor?.apply()

        mActivity?.let { mInteractor?.findWeatherForecast(it, location, this) }
    }

    override fun onUserLocationFailedToBeRetrieved() {
        val errorMessage = mActivity?.let { it.getString(R.string.dialog_location_request_failed_message) }
                ?: return
        mView?.showErrorDialog(errorMessage)
    }


    // MARK: InteractorInterface.WeatherForecastListener

    override fun foundWeatherForecast(result: List<CityForecast>) {
        mView?.loadCityForecastList(result)
    }

    override fun emptyWeatherForecast() {
        mView?.loadCityForecastList(ArrayList())
    }

    override fun errorWhileFetchingWeatherForecast() {
        val errorMessage = mActivity?.let { it.getString(R.string.dialog_location_request_failed_message) }
                ?: return
        mView?.showErrorDialog(errorMessage)
    }
}
