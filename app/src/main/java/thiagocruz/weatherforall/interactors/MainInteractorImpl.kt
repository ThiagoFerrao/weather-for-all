package thiagocruz.weatherforall.interactors

import android.content.Context
import android.location.Location
import retrofit2.Call
import retrofit2.Response
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.entities.CityForecast
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
                        val result = response?.body()?.cityForecastList
                                ?: return listener.errorWhileFetchingWeatherForecast()

                        if (result.isEmpty()) return listener.emptyWeatherForecast()

                        sortCityForecastListByUserDistance(result, context, listener)
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

    private fun sortCityForecastListByUserDistance(list: List<CityForecast>, context: Context, listener: MainInteractor.WeatherForecastListener) {
        val preferences = context.getSharedPreferences(Constant.SharedPreferences.DEFAULT, Context.MODE_PRIVATE)
        val userLatitude = preferences.getString(Constant.SharedPreferences.KEY_USER_LOCATION_LATITUDE, null)?.toDouble()
        val userLongitude = preferences.getString(Constant.SharedPreferences.KEY_USER_LOCATION_LONGITUDE, null)?.toDouble()

        if (userLatitude == null || userLongitude == null) return listener.foundWeatherForecast(list)

        val sortedList = list.sortedWith(compareBy { it.coordinate.distanceToLatLngInKm(userLatitude, userLongitude) })

        listener.foundWeatherForecast(sortedList)
    }
}
