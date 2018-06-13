package thiagocruz.weatherforall.utils

import retrofit2.Response
import java.io.IOException

object ServiceErrorUtil {

    fun hasInternalError(response: Response<*>?): Boolean {
        return response == null || response.code() in 400..598
    }

    fun hasConnectionError(t: Throwable?): Boolean {
        return t is IOException
    }
}
