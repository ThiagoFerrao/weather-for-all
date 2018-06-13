package thiagocruz.weatherforall.interactors

import android.location.Location
import thiagocruz.weatherforall.services.WeatherForecastService
import thiagocruz.weatherforall.utils.LocationConverterUtil

class MainInteractorImpl : MainInteractor {

    override fun findWeatherForecast(location: Location, listener: MainInteractor.WeatherForecastListener) {
        val forecastPerimeter = LocationConverterUtil.getDefaultPerimeter(location)

        WeatherForecastService.getWeatherForecast(forecastPerimeter + ",10", "metric")
    }
}
