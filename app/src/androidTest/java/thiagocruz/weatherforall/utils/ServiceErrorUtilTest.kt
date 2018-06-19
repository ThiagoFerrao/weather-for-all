package thiagocruz.weatherforall.utils

import com.google.gson.JsonSyntaxException
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Response
import thiagocruz.weatherforall.entities.WeatherApiResponse
import java.io.IOException


class ServiceErrorUtilTest {

    private val testResponseWithStatusCode400: Response<WeatherApiResponse> = Response.error(400, ResponseBody.create(MediaType.parse(""), ""))
    private val testResponseWithStatusCode600: Response<WeatherApiResponse> = Response.error(600, ResponseBody.create(MediaType.parse(""), ""))

    @Test
    fun testHasInternalError() {
        assert(ServiceErrorUtil.hasInternalError(testResponseWithStatusCode400))
        assert(!ServiceErrorUtil.hasInternalError(testResponseWithStatusCode600))
    }

    @Test
    fun testHasConnectionError() {
        assert(ServiceErrorUtil.hasConnectionError(IOException()))
        assert(!ServiceErrorUtil.hasConnectionError(IllegalAccessException()))
    }

    @Test
    fun testHasApiError() {
        assert(ServiceErrorUtil.hasApiError(JsonSyntaxException("")))
        assert(!ServiceErrorUtil.hasApiError(IllegalAccessException()))
    }
}
