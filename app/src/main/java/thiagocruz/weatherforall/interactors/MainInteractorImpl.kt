package thiagocruz.weatherforall.interactors

import android.content.Context
import android.location.Location
import retrofit2.Call
import retrofit2.Response
import thiagocruz.weatherforall.entities.WeatherApiResponse
import thiagocruz.weatherforall.managers.TemperatureUnitManager
import thiagocruz.weatherforall.services.BaseCallback
import thiagocruz.weatherforall.services.WeatherForecastService
import thiagocruz.weatherforall.utils.LocationPerimeterUtil

class MainInteractorImpl : MainInteractor {

    override fun findWeatherForecast(context: Context, location: Location, listener: MainInteractor.WeatherForecastListener) {
        val forecastPerimeter = LocationPerimeterUtil.getDefaultPerimeter(location)
        val temperatureUnit = TemperatureUnitManager.getCurrentTemperatureUnit(context)

        WeatherForecastService.getWeatherForecast(forecastPerimeter, temperatureUnit,
                object : BaseCallback<WeatherApiResponse> {
                    override fun onResponse(call: Call<WeatherApiResponse>?, response: Response<WeatherApiResponse>?) {
                        val result = response?.body()?.cityForecastList ?: return listener.errorWhileFetchingWeatherForecast()

                        if (result.isEmpty()) return listener.emptyWeatherForecast()

                        listener.foundWeatherForecast(result)
                    }

                    override fun onInternalError(call: Call<WeatherApiResponse>?, response: Response<WeatherApiResponse>?) {
                        listener.errorWhileFetchingWeatherForecast()
                    }

                    override fun onConnectionError(call: Call<WeatherApiResponse>?, t: Throwable?) {
                        listener.errorWhileFetchingWeatherForecast()
                    }

                    override fun onGeneralError(call: Call<WeatherApiResponse>?, t: Throwable?) {
                        listener.errorWhileFetchingWeatherForecast()
                    }
                })
    }
}
