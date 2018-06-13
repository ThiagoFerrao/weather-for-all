package thiagocruz.weatherforall.entities

import com.google.gson.annotations.SerializedName

data class CityCoordinate (
        @SerializedName("lon")
        val longitude : Double,
        @SerializedName("lat")
        val latitude : Double
)
