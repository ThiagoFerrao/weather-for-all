package thiagocruz.weatherforall.utils

import com.google.gson.JsonSyntaxException
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Test
import retrofit2.Response
import thiagocruz.weatherforall.entities.WeatherApiResponse
import java.io.IOException


class ServiceErrorUtilTest {

    private val testResponseWithStatusCode404: Response<WeatherApiResponse> = Response.error(404, ResponseBody.create(MediaType.parse(""), ""))
    private val testResponseWithStatusCode200: Response<WeatherApiResponse> = Response.success(WeatherApiResponse(ArrayList()))

    @Test
    fun testHasInternalError() {
        assert(ServiceErrorUtil.hasInternalError(testResponseWithStatusCode404))
        assert(!ServiceErrorUtil.hasInternalError(testResponseWithStatusCode200))
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
