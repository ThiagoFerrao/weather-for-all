package thiagocruz.weatherforall.entities

import com.google.gson.annotations.SerializedName

data class CityCoordinate (
        @SerializedName("Lon")
        val longitude : Double,
        @SerializedName("Lat")
        val latitude : Double
)
