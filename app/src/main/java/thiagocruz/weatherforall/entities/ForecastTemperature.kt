package thiagocruz.weatherforall.entities

import com.google.gson.annotations.SerializedName

data class ForecastTemperature(
        @SerializedName("temp")
        val averageTemp: Double,
        @SerializedName("temp_min")
        val minimumTemp: Double,
        @SerializedName("temp_max")
        val maximumTemp: Double
)
