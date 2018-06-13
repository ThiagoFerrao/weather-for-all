package thiagocruz.weatherforall.entities

import com.google.gson.annotations.SerializedName

data class ForecastWeather(
        @SerializedName("main")
        val weatherTitle: String,
        @SerializedName("description")
        val weatherDescription: String,
        @SerializedName("icon")
        val weatherIconID: String
) {
    fun getWheaterImageUrl(): String {
        return "http://openweathermap.org/img/w/${this.weatherIconID}"
    }
}
