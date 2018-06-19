package thiagocruz.weatherforall.utils

import android.location.Location
import org.junit.Test
import kotlin.math.PI
import kotlin.math.cos

class LocationPerimeterUtilTest {

    private val defaultPerimeterRadius = 50.0
    private val defaultPerimeterZoom = 50
    private val earthRadius = 6371.0
    private val halfTurn = 180.0

    @Test
    fun testGetDefaultPerimeter() {
        val testLocation = Location("")
        testLocation.latitude = 10.0
        testLocation.longitude = 10.0

        val testDefaultPerimeter = LocationPerimeterUtil.getDefaultPerimeter(testLocation)

        val topLat = 10.0 + (defaultPerimeterRadius / earthRadius) * (halfTurn / PI)
        val bottomLat = 10.0 + (-defaultPerimeterRadius / earthRadius) * (halfTurn / PI)
        val leftLong = 10.0 + (defaultPerimeterRadius / earthRadius) * (halfTurn / PI) / cos(10.0 * PI / halfTurn)
        val rightLong = 10.0 + (-defaultPerimeterRadius / earthRadius) * (halfTurn / PI) / cos(10.0 * PI / halfTurn)

        val expectDefaultPerimeter = doubleArrayOf(leftLong, bottomLat, rightLong, topLat).joinToString(",") + ",$defaultPerimeterZoom"

        assert(testDefaultPerimeter == expectDefaultPerimeter)
    }

    @Test
    fun testGetNewLatitudeFrom() {
        val testNewLatitude = LocationPerimeterUtil.getNewLatitudeFrom(10.0, defaultPerimeterRadius)
        val expectNewLatitude = 10.0 + (defaultPerimeterRadius / earthRadius) * (halfTurn / PI)

        assert(testNewLatitude == expectNewLatitude)
    }

    @Test
    fun testGetNewLongitudeFrom() {
        val testNewLongitude = LocationPerimeterUtil.getNewLongitudeFrom(10.0, 10.0, defaultPerimeterRadius)
        val expectNewLongitude = 10.0 + (defaultPerimeterRadius / earthRadius) * (halfTurn / PI) / cos(10.0 * PI / halfTurn)

        assert(testNewLongitude == expectNewLongitude)
    }
}
