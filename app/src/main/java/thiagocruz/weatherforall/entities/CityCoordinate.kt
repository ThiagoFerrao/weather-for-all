package thiagocruz.weatherforall.entities

import com.google.gson.annotations.SerializedName
import thiagocruz.weatherforall.utils.DistanceBetweenUtil

data class CityCoordinate(
        @SerializedName("Lon")
        val longitude: Double,
        @SerializedName("Lat")
        val latitude: Double
) {
    fun distanceToLatLngInKm(latitude: Double, longitude: Double): Double {
        return DistanceBetweenUtil.getTheDistanceBetweenLatLngsInKm(latitude, longitude, this.latitude, this.longitude)
    }
}
