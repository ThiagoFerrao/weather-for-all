package thiagocruz.weatherforall.presenters

import android.app.Activity
import android.content.Intent
import android.view.MenuItem
import thiagocruz.weatherforall.views.View

interface Presenter {
    fun attachView(view: View, activity: Activity)
    fun getUserLocation()
    fun locationPermissionRequestOpeningSettingsAccepted()
    fun locationPermissionRequestAccepted(permission: String)
    fun handleActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    fun handleRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    fun handleChangeMetrics(item: MenuItem?)
    fun handleChangeToMap()
}
