package thiagocruz.weatherforall.interactors

import android.location.Location
import retrofit2.Call
import retrofit2.Response
import thiagocruz.weatherforall.entities.WeatherApiResponse
import thiagocruz.weatherforall.services.BaseCallback
import thiagocruz.weatherforall.services.WeatherForecastService
import thiagocruz.weatherforall.utils.LocationConverterUtil

class MainInteractorImpl : MainInteractor {

    override fun findWeatherForecast(location: Location, listener: MainInteractor.WeatherForecastListener) {
        val forecastPerimeter = LocationConverterUtil.getDefaultPerimeter(location)

        WeatherForecastService.getWeatherForecast("$forecastPerimeter,20", "metric",
                object : BaseCallback<WeatherApiResponse> {
                    override fun onResponse(call: Call<WeatherApiResponse>?, response: Response<WeatherApiResponse>?) {

                    }

                    override fun onInternalError(call: Call<WeatherApiResponse>?, response: Response<WeatherApiResponse>?) {

                    }

                    override fun onConnectionError(call: Call<WeatherApiResponse>?, t: Throwable?) {

                    }

                    override fun onGeneralError(call: Call<WeatherApiResponse>?, t: Throwable?) {

                    }
                })
    }
}
