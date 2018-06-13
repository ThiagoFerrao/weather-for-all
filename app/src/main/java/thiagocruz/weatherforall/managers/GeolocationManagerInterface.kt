package thiagocruz.weatherforall.managers

import android.location.Location
import com.google.android.gms.common.api.Status

interface GeolocationManagerInterface {
    interface Listener {
        fun onPermissionNeedToBeApproved(permission: String)
        fun onPermissionDeniedPermanently()
        fun onLocationNeedToBeTurnedOn(status: Status)

        fun onUserLocationSuccessfullyRetrieved(location: Location)
        fun onUserLocationFailedToBeRetrieved()
    }
}