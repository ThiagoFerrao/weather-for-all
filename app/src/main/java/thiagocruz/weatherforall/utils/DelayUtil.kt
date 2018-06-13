package thiagocruz.weatherforall.utils

import android.os.Handler

object DelayUtil {

    private const val STANDARD_DELAY_INTERVAL: Long = 2000

    fun standardDelay(callback: () -> Unit) {
        delay(STANDARD_DELAY_INTERVAL, callback)
    }

    fun delay(milliseconds: Long, callback: () -> Unit) {
        Handler().postDelayed(callback, milliseconds)
    }
}
