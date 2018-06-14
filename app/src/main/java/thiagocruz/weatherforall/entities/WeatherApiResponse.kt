package thiagocruz.weatherforall.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class WeatherApiResponse(
        @SerializedName("list")
        val cityForecastList: List<CityForecast>
) : Parcelable {
    constructor(source: Parcel) : this(
            source.createTypedArrayList(CityForecast.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(cityForecastList)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<WeatherApiResponse> = object : Parcelable.Creator<WeatherApiResponse> {
            override fun createFromParcel(source: Parcel): WeatherApiResponse = WeatherApiResponse(source)
            override fun newArray(size: Int): Array<WeatherApiResponse?> = arrayOfNulls(size)
        }
    }
}
