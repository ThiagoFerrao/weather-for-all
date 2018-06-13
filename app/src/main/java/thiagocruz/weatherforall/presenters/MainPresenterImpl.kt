package thiagocruz.weatherforall.presenters

import android.app.Activity
import android.location.Location
import com.google.android.gms.common.api.Status
import thiagocruz.weatherforall.interactors.MainInteractor
import thiagocruz.weatherforall.interactors.MainInteractorImpl
import thiagocruz.weatherforall.managers.GeolocationManager
import thiagocruz.weatherforall.managers.GeolocationManagerInterface
import thiagocruz.weatherforall.views.MainView

class MainPresenterImpl : MainPresenter, GeolocationManagerInterface.Listener, MainInteractor.WeatherForecastListener {

    private var mView: MainView? = null
    private var mInteractor: MainInteractor? = null


    // MARK: MainPresenter

    override fun attachView(mainView: MainView) {
        this.mView = mainView
        mInteractor = MainInteractorImpl()
    }

    override fun getUserLocation(activity: Activity) {
        GeolocationManager.getUserLocation(activity, this)
    }

    override fun locationPermissionRequestOpeningSettingsAccepted() {
        mView?.showAppSettingsScreen()
    }

    override fun locationPermissionRequestAccepted(permission: String) {
        mView?.requestPermission(permission)
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
