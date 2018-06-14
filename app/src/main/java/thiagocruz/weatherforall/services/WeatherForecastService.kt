package thiagocruz.weatherforall.services

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import thiagocruz.weatherforall.BuildConfig
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.entities.WeatherApiResponse
import thiagocruz.weatherforall.utils.RetrofitUtil
import thiagocruz.weatherforall.utils.ServiceErrorUtil

object WeatherForecastService {

    fun getWeatherForecast(perimeter: String, temperatureUnit: String, callback: BaseCallback<WeatherApiResponse>) {
        val weatherForecastService = RetrofitUtil.buildRequest().create(WeatherForecastRetrofitService::class.java)
        val call = weatherForecastService.forecast(perimeter, temperatureUnit, Constant.AppLanguage.PORTUGUESE, BuildConfig.OPEN_WEATHER_MAP_API_KEY)

        call.enqueue(object : Callback<WeatherApiResponse> {
            override fun onResponse(call: Call<WeatherApiResponse>?, response: Response<WeatherApiResponse>?) {
                if (ServiceErrorUtil.hasInternalError(response)) return callback.onInternalError(call, response)

                callback.onResponse(call, response)
            }

            override fun onFailure(call: Call<WeatherApiResponse>?, t: Throwable?) {
                if (ServiceErrorUtil.hasConnectionError(t)) return callback.onConnectionError(call, t)

                callback.onGeneralError(call, t)
            }
        })
    }
}
