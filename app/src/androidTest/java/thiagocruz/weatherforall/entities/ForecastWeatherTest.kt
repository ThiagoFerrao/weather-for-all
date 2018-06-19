package thiagocruz.weatherforall.entities

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Handler
import android.support.annotation.UiThread
import android.webkit.URLUtil
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import org.junit.Before
import org.junit.Test
import java.lang.Exception
import android.os.Looper


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

    @Test
    @UiThread
    fun testGetWeatherImageUrlIsLoaded() {
        val weatherIcon = testForecastWeather?.getWeatherImageUrl()

        Handler(Looper.getMainLooper()).post {
            Picasso.get()
                    .load(weatherIcon)
                    .into(object : Target {
                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                            AssertionError("testGetWeatherImageUrlIsLoaded - onBitmapFailed")
                        }

                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        }
                    })
        }
    }
}
