package thiagocruz.weatherforall.entities

import android.webkit.URLUtil
import org.junit.Before
import org.junit.Test

class ForecastWeatherTest {

    private var testForecastWeather: ForecastWeather? = null
    private val testMain = "Rain"
    private val testDescription = "light rain"
    private val testIcon = "10d"

    @Before
    fun setup() {
        testForecastWeather = ForecastWeather(testMain, testDescription, testIcon)
    }

    @Test
    fun testGetWeatherImageUrlIsValidUrl() {
        val weatherIcon = testForecastWeather?.getWeatherImageUrl()

        assert(URLUtil.isValidUrl(weatherIcon))
    }
}
