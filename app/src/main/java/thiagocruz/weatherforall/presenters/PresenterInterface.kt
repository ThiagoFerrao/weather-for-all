package thiagocruz.weatherforall.presenters

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import com.google.android.gms.maps.model.LatLng
import thiagocruz.weatherforall.views.ViewInterface

interface PresenterInterface {
    fun attachView(view: ViewInterface, activity: Activity)
    fun initializeWithIntent(intent: Intent?)
    fun mapCameraPositionUpdated(newCameraLatLng: LatLng?)
    fun getUserLocation()
    fun locationPermissionRequestOpeningSettingsAccepted()
    fun locationPermissionRequestAccepted(permission: String)
    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun handleRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    fun handleChangeMetrics(item: MenuItem?)
    fun handleChangeScreen()
}
