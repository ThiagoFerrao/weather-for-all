package thiagocruz.weatherforall.services

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import thiagocruz.weatherforall.BuildConfig
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.entities.WeatherApiResponse
import thiagocruz.weatherforall.utils.RetrofitUtil

object WeatherForecastService {

    fun getWeatherForecast(perimeter: String, temperatureUnit: String) {
        val weatherForecastService = RetrofitUtil.buildRequest().create(WeatherForecastRetrofitService::class.java)
        val call = weatherForecastService.forecast(perimeter, temperatureUnit, Constant.AppLanguage.PORTUGUESE, BuildConfig.OPEN_WHEATER_MAP_API_KEY)

        call.enqueue(object : Callback<WeatherApiResponse> {
            override fun onResponse(call: Call<WeatherApiResponse>?, response: Response<WeatherApiResponse>?) {
                val aux = response?.body()
                print(aux)
            }

            override fun onFailure(call: Call<WeatherApiResponse>?, t: Throwable?) {
                val aux = call.toString()
                print(aux)
            }
        })
    }
}
