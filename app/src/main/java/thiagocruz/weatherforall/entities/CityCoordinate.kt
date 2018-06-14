package thiagocruz.weatherforall.entities

import android.os.Parcel
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import thiagocruz.weatherforall.utils.DistanceBetweenUtil

data class CityCoordinate(
        @SerializedName("Lon")
        val longitude: Double,
        @SerializedName("Lat")
        val latitude: Double
) : Parcelable {
    fun getCityLatLng(): LatLng {
        return LatLng(latitude, longitude)
    }

    fun distanceToLatLngInKm(latitude: Double, longitude: Double): Double {
        return DistanceBetweenUtil.getTheDistanceBetweenLatLngsInKm(latitude, longitude, this.latitude, this.longitude)
    }

    constructor(source: Parcel) : this(
            source.readDouble(),
            source.readDouble()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeDouble(longitude)
        writeDouble(latitude)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CityCoordinate> = object : Parcelable.Creator<CityCoordinate> {
            override fun createFromParcel(source: Parcel): CityCoordinate = CityCoordinate(source)
            override fun newArray(size: Int): Array<CityCoordinate?> = arrayOfNulls(size)
        }
    }
}
