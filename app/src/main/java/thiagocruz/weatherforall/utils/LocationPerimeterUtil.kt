package thiagocruz.weatherforall.utils

import android.location.Location
import kotlin.math.PI
import kotlin.math.cos

// Logic Found On The Link Below
// https://stackoverflow.com/questions/7477003/calculating-new-longitude-latitude-from-old-n-meters?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa

object LocationPerimeterUtil {

    private const val DEFAULT_PERIMETER_RADIUS: Double = 50.0
    private const val DEFAULT_PERIMETER_ZOOM: Int = 20
    private const val EARTH_RADIUS: Double = 6371.0
    private const val HALF_TURN: Double = 180.0

    fun getDefaultPerimeter(currentLocation: Location): String {
        val currentLatitude = currentLocation.latitude
        val currentLongitude = currentLocation.longitude

        val topLat = getNewLatitudeFrom(currentLatitude, DEFAULT_PERIMETER_RADIUS)
        val bottomLat = getNewLatitudeFrom(currentLatitude, -DEFAULT_PERIMETER_RADIUS)
        val leftLong = getNewLongitudeFrom(currentLatitude, currentLongitude, DEFAULT_PERIMETER_RADIUS)
        val rightLong = getNewLongitudeFrom(currentLatitude, currentLongitude, -DEFAULT_PERIMETER_RADIUS)

        return doubleArrayOf(leftLong, bottomLat, rightLong, topLat).joinToString(",") + ",$DEFAULT_PERIMETER_ZOOM"
    }

    fun getNewLatitudeFrom(currentLatitude: Double, addKilometers: Double): Double {
        return currentLatitude + (addKilometers / EARTH_RADIUS) * (HALF_TURN / PI)
    }

    fun getNewLongitudeFrom(currentLatitude: Double, currentLongitude: Double, addKilometers: Double): Double {
        return currentLongitude + (addKilometers / EARTH_RADIUS) * (HALF_TURN / PI) / cos(currentLatitude * PI / HALF_TURN)
    }
}
