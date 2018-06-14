package thiagocruz.weatherforall.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CityForecast(
        @SerializedName("name")
        val name: String,
        @SerializedName("coord")
        val coordinate: CityCoordinate,
        @SerializedName("main")
        val temperature: ForecastTemperature,
        @SerializedName("weather")
        val weather: List<ForecastWeather>
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readParcelable<CityCoordinate>(CityCoordinate::class.java.classLoader),
            source.readParcelable<ForecastTemperature>(ForecastTemperature::class.java.classLoader),
            source.createTypedArrayList(ForecastWeather.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeParcelable(coordinate, 0)
        writeParcelable(temperature, 0)
        writeTypedList(weather)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CityForecast> = object : Parcelable.Creator<CityForecast> {
            override fun createFromParcel(source: Parcel): CityForecast = CityForecast(source)
            override fun newArray(size: Int): Array<CityForecast?> = arrayOfNulls(size)
        }
    }
}
