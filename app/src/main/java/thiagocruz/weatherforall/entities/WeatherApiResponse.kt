package thiagocruz.weatherforall.entities

import com.google.gson.annotations.SerializedName

data class WeatherApiResponse(
        @SerializedName("list")
        val cityForecastList: List<CityForecast>
)
