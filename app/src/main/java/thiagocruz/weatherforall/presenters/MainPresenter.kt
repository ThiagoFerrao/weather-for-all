package thiagocruz.weatherforall.presenters

import android.app.Activity
import thiagocruz.weatherforall.views.MainView

interface MainPresenter {
    fun attachView(mainView: MainView)
    fun getUserLocation(activity: Activity)
    fun locationPermissionRequestOpeningSettingsAccepted()
    fun locationPermissionRequestAccepted(permission: String)
}
