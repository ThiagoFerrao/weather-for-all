package thiagocruz.weatherforall.utils

import android.location.Location
import org.junit.Before
import org.junit.Test

class DistanceBetweenUtilTest {

    var locationA: Location? = null
    var locationB: Location? = null

    @Before
    fun setup() {
        locationA = Location("")
        locationA?.latitude = 10.0
        locationA?.longitude = 10.0

        locationB = Location("")
        locationB?.latitude = 20.0
        locationB?.longitude = 20.0
    }

    @Test
    fun testGetTheDistanceBetweenLatLngsInKm() {
        val expectDist = (locationA?.distanceTo(locationB)!! / 1000).toDouble()
        val testDist = DistanceBetweenUtil.getTheDistanceBetweenLatLngsInKm(locationA?.latitude!!, locationA?.longitude!!, locationB?.latitude!!, locationB?.longitude!!)

        assert(expectDist == testDist)
    }


}
