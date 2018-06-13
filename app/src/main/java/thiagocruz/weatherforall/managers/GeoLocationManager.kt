package thiagocruz.weatherforall.managers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes

object GeolocationManager {

    fun getUserLocation(activity: Activity, listener: GeolocationManagerInterface.Listener) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
            return listener.onPermissionDeniedPermanently()
        }

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return listener.onPermissionNeedToBeApproved(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        val googleApiClient = GoogleApiClient.Builder(activity).addApi(LocationServices.API).build()
        googleApiClient.connect()

        val locationRequest = LocationRequest.create()
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)

        val task = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())

        task.setResultCallback { result ->
            val status = result.status

            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> {
                    getUserLastLocationAvailable(activity, listener)
                    googleApiClient.disconnect()
                }

                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                    listener.onLocationNeedToBeTurnedOn(status)
                    googleApiClient.disconnect()
                }

                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                    listener.onUserLocationFailedToBeRetrieved()
                    googleApiClient.disconnect()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUserLastLocationAvailable(activity: Activity, listener: GeolocationManagerInterface.Listener) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        fusedLocationClient.lastLocation.addOnCompleteListener(activity) { task ->
            if (!task.isSuccessful || task.result == null) {
                getUserCurrentLocation(activity, listener)

            } else {
                val lastLocation = task.result
                listener.onUserLocationSuccessfullyRetrieved(lastLocation)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getUserCurrentLocation(activity: Activity, listener: GeolocationManagerInterface.Listener) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

        val locationRequest = LocationRequest()
                .setInterval(20000)
                .setFastestInterval(10000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                val locations = locationResult?.locations

                if (locations == null || locations.isEmpty()) {
                    return listener.onUserLocationFailedToBeRetrieved()
                }

                listener.onUserLocationSuccessfullyRetrieved(locations[0])
                fusedLocationClient.removeLocationUpdates(this)
            }
        }, null)
    }
}
