package thiagocruz.weatherforall.entities

import com.google.gson.annotations.SerializedName

data class CityForecast(
        @SerializedName("name")
        val name: String,
        @SerializedName("coord")
        val coordinate: CityCoordinate,
        @SerializedName("main")
        val temperature: ForecastTemperature,
        @SerializedName("weather")
        val weather: ForecastWeather
)
