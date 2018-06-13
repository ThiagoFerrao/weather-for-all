package thiagocruz.weatherforall.presenters

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import com.google.android.gms.common.api.Status
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.interactors.MainInteractor
import thiagocruz.weatherforall.interactors.MainInteractorImpl
import thiagocruz.weatherforall.managers.GeolocationManager
import thiagocruz.weatherforall.managers.GeolocationManagerInterface
import thiagocruz.weatherforall.views.MainView

class MainPresenterImpl : MainPresenter, GeolocationManagerInterface.Listener, MainInteractor.WeatherForecastListener {

    private var mView: MainView? = null
    private var mActivity: Activity? = null
    private var mInteractor: MainInteractor? = null


    // MARK: MainPresenter

    override fun attachView(mainView: MainView, activity: Activity) {
        mView = mainView
        mActivity = activity
        mInteractor = MainInteractorImpl()

        mView?.setupViewContent()
    }

    override fun getUserLocation() {
        mActivity?.let { GeolocationManager.getUserLocation(it, this) }
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
                    return print("[MainPresenterImpl] User Declined The Location Permission Request")
                }

                mActivity?.let { GeolocationManager.getUserLocation(it, this) }
            }
        }
    }

    override fun handleRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            Constant.PermissionRequestCode.GEOLOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mActivity?.let { GeolocationManager.getUserLocation(it, this) }
                    return
                }

                print("[MainPresenterImpl] User Declined Turning On The Location")
            }
        }
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
        mInteractor?.findWeatherForecast(location, this)
    }

    override fun onUserLocationFailedToBeRetrieved() {
        mView?.showLocationRequestFailedDialog()
    }


    // MARK: MainInteractor.WeatherForecastListener


}