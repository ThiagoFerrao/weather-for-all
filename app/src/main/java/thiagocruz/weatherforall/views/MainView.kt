package thiagocruz.weatherforall.views

import com.google.android.gms.common.api.Status

interface MainView {
    fun setupViewContent()
    fun showLocationPermissionRequestDialog(permission: String)
    fun showLocationPermissionDeniedPermanentlyDialog()
    fun showLocationToBeTurnedOnRequest(status: Status)
    fun showErrorDialog(message: String)
    fun requestPermission(permission: String)
    fun showAppSettingsScreen()
}