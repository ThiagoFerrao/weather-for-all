package thiagocruz.weatherforall.utils

import android.location.Location

object DistanceBetweenUtil {

    fun getTheDistanceBetweenLatLngsInKm(startLatitude: Double, startLongitude: Double, endLatitude: Double, endLongitude: Double): Double {
        val locationA = Location("")
        locationA.latitude = startLatitude
        locationA.longitude = startLongitude

        val locationB = Location("")
        locationB.latitude = endLatitude
        locationB.longitude = endLongitude

        return (locationA.distanceTo(locationB) / 1000).toDouble()
    }
}
