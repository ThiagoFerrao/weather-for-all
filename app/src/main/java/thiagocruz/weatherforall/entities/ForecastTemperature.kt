package thiagocruz.weatherforall.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ForecastTemperature(
        @SerializedName("temp")
        val averageTemp: Double,
        @SerializedName("temp_min")
        val minimumTemp: Double,
        @SerializedName("temp_max")
        val maximumTemp: Double
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readDouble(),
            source.readDouble(),
            source.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeDouble(averageTemp)
        writeDouble(minimumTemp)
        writeDouble(maximumTemp)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ForecastTemperature> = object : Parcelable.Creator<ForecastTemperature> {
            override fun createFromParcel(source: Parcel): ForecastTemperature = ForecastTemperature(source)
            override fun newArray(size: Int): Array<ForecastTemperature?> = arrayOfNulls(size)
        }
    }
}
