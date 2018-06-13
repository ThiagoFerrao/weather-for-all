package thiagocruz.weatherforall.views

import com.google.android.gms.common.api.Status

interface MainView {
    fun showLocationPermissionRequestDialog(permission: String)
    fun showLocationPermissionDeniedPermanentlyDialog()
    fun showLocationToBeTurnedOnRequest(status: Status)
    fun showLocationRequestFailedDialog()
    fun requestPermission(permission: String)
    fun showAppSettingsScreen()
}