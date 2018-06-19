package thiagocruz.weatherforall.entities

import com.google.android.gms.maps.model.LatLng
import org.junit.Before
import org.junit.Test
import thiagocruz.weatherforall.utils.DistanceBetweenUtil

class CityCoordinateTest {

    private var testCityCoordinate: CityCoordinate? = null
    private val testLatitudeOne = 10.0
    private val testLongitudeOne = 10.0
    private val testLatitudeTwo = 10.0
    private val testLongitudeTwo = 10.0

    @Before
    fun setUp() {
        testCityCoordinate = CityCoordinate(testLatitudeOne, testLongitudeOne)
    }

    @Test
    fun testGetCityLatLng() {
        val testLatLng = testCityCoordinate?.getCityLatLng()
        val expectLatLng = LatLng(testLatitudeOne, testLongitudeOne)

        assert(expectLatLng == testLatLng)
    }

    @Test
    fun testDistanceToLatLngInKm() {
        val testDist = testCityCoordinate?.distanceToLatLngInKm(testLatitudeTwo, testLongitudeTwo)
        val expectDist = DistanceBetweenUtil.getTheDistanceBetweenLatLngsInKm(testCityCoordinate?.latitude!!, testCityCoordinate?.longitude!!, testLatitudeTwo, testLatitudeTwo)

        assert(expectDist == testDist)
    }
}
