package thiagocruz.weatherforall.interactors

import android.content.Context
import android.location.Location
import thiagocruz.weatherforall.entities.CityForecast

interface InteractorInterface {
    fun findWeatherForecast(context: Context, location: Location, listener: WeatherForecastListener)

    interface WeatherForecastListener {
        fun foundWeatherForecast(result: List<CityForecast>)
        fun emptyWeatherForecast()
        fun errorWhileFetchingWeatherForecast()
    }
}
