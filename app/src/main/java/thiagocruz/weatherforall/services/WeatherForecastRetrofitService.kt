package thiagocruz.weatherforall.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import thiagocruz.weatherforall.entities.WeatherApiResponse

interface WeatherForecastRetrofitService {

    @GET("box/city")
    fun forecast(@Query("bbox") perimeter: String,
                 @Query("units") temperatureUnit: String,
                 @Query("lang") language: String,
                 @Query("appid") apiKey: String
    ): Call<WeatherApiResponse>
}
