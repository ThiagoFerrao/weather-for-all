package thiagocruz.weatherforall.interactors

import android.location.Location
import thiagocruz.weatherforall.entities.CityForecast

interface MainInteractor {
    fun findWeatherForecast(location: Location, listener: WeatherForecastListener)

    interface WeatherForecastListener {
        fun foundWeatherForecast(result: List<CityForecast>)
        fun emptyWeatherForecast()
        fun errorWhileFetchingWeatherForecast()
    }
}
