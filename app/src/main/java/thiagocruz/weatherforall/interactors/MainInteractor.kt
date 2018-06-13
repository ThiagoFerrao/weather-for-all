package thiagocruz.weatherforall.interactors

import android.location.Location

interface MainInteractor {
    fun findWeatherForecast(location: Location, listener: WeatherForecastListener)

    interface WeatherForecastListener {

    }
}
