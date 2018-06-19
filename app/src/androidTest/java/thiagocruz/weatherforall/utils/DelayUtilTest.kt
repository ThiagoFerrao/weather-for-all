package thiagocruz.weatherforall.utils

import android.os.Handler
import android.os.Looper
import org.junit.Test
import java.util.*

class DelayUtilTest {

    private val standardDelayInterval: Long = 2000
    private val testDelayInterval: Long = 1000

    @Test
    fun testDelay() {
        val startDate = Date()

        Handler(Looper.getMainLooper()).post {
            DelayUtil.delay(testDelayInterval) {
                val endDate = Date()
                val diffDate = (endDate.time - startDate.time) / 1000000

                assert(diffDate == testDelayInterval)
            }
        }
    }

    @Test
    fun testStandardDelay() {
        val startDate = Date()

        Handler(Looper.getMainLooper()).post {
            DelayUtil.standardDelay {
                val endDate = Date()
                val diffDate = (endDate.time - startDate.time) / 1000000

                assert(diffDate == standardDelayInterval)
            }
        }
    }
}
