package thiagocruz.weatherforall.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ForecastWeather(
        @SerializedName("main")
        val weatherTitle: String,
        @SerializedName("description")
        val weatherDescription: String,
        @SerializedName("icon")
        val weatherIconID: String
) : Parcelable {
    fun getWeatherImageUrl(): String {
        return "http://openweathermap.org/img/w/${this.weatherIconID}.png"
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(weatherTitle)
        writeString(weatherDescription)
        writeString(weatherIconID)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ForecastWeather> = object : Parcelable.Creator<ForecastWeather> {
            override fun createFromParcel(source: Parcel): ForecastWeather = ForecastWeather(source)
            override fun newArray(size: Int): Array<ForecastWeather?> = arrayOfNulls(size)
        }
    }
}
