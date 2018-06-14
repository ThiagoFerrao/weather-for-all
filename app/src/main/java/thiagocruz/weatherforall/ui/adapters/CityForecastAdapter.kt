package thiagocruz.weatherforall.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_city_forecast.view.*
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.R
import thiagocruz.weatherforall.entities.CityForecast
import thiagocruz.weatherforall.managers.TemperatureUnitManager

class CityForecastAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mList: List<CityForecast>? = null
    private var isListEmpty = { (mList?.size == null || mList?.size == 0) }

    fun setCityForecastList(cityForecastList: List<CityForecast>) {
        mList = cityForecastList
    }

    override fun getItemCount(): Int {
        if (isListEmpty()) return 1

        return mList!!.size
    }

    override fun getItemViewType(position: Int): Int {
        if (isListEmpty()) return Constant.CityForecastAdapter.EMPTY_ITEM_VIEW_TYPE

        return Constant.CityForecastAdapter.CONTENT_ITEM_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == Constant.CityForecastAdapter.CONTENT_ITEM_VIEW_TYPE) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_city_forecast, parent, false)
            return CityForecastItemViewHolder(itemView)
        }

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.adapter_empty_city_forecast, parent, false)
        return EmptyItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == Constant.CityForecastAdapter.EMPTY_ITEM_VIEW_TYPE) {
            return
        }

        val cityForecastItemViewHolder = holder as CityForecastItemViewHolder
        mList?.get(position)?.let { cityForecastItemViewHolder.onBindViewHolder(context, it) }
    }

    class CityForecastItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBindViewHolder(context: Context, cityForecast: CityForecast) {
            setupCityName(cityForecast)
            setupDistanceValue(context, cityForecast)
            setupWeatherIcon(cityForecast)
            setupWeatherDescription(cityForecast)
            setupTemperatures(context, cityForecast)
        }

        private fun setupCityName(cityForecast: CityForecast) {
            itemView.textViewCityName.text = cityForecast.name
        }

        private fun setupDistanceValue(context: Context, cityForecast: CityForecast) {
            val preferences = context.getSharedPreferences(Constant.SharedPreferences.DEFAULT, Context.MODE_PRIVATE)

            val userLatitude = preferences.getString(Constant.SharedPreferences.KEY_USER_LOCATION_LATITUDE, null)?.toDouble()
            val userLongitude = preferences.getString(Constant.SharedPreferences.KEY_USER_LOCATION_LONGITUDE, null)?.toDouble()

            val distanceText = if (userLatitude == null || userLongitude == null) {
                "Dist: ?"
            } else {
                String.format("Dist: %.1f Km", cityForecast.coordinate.distanceToLatLngInKm(userLatitude, userLongitude))
            }

            itemView.textViewDistanceToUser.text = distanceText
        }

        private fun setupWeatherIcon(cityForecast: CityForecast) {
            Picasso.get()
                    .load(cityForecast.weather.first().getWeatherImageUrl())
                    .placeholder(R.drawable.logo_splash)
                    .error(R.drawable.logo_splash)
                    .into(itemView.imageViewWeatherIcon)
        }

        private fun setupWeatherDescription(cityForecast: CityForecast) {
            val descriptionArrayCapitalize = ArrayList<String>()
            val descriptionArray = cityForecast.weather.first().weatherDescription.split(" ")
            for (word in descriptionArray) {
                descriptionArrayCapitalize.add(word.capitalize())
            }

            itemView.textViewWeatherDescription.text = descriptionArrayCapitalize.joinToString(" ")
        }

        private fun setupTemperatures(context: Context, cityForecast: CityForecast) {
            val unitSuffix = TemperatureUnitManager.getTemperatureUnitSuffix(context)

            itemView.textViewMaxTemp.text = String.format("Min: %.1f$unitSuffix", cityForecast.temperature.minimumTemp)
            itemView.textViewMinTemp.text = String.format("Max: %.1f$unitSuffix", cityForecast.temperature.maximumTemp)
            itemView.textViewMainTemp.text = String.format("%.0f$unitSuffix", cityForecast.temperature.averageTemp)
        }
    }

    class EmptyItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
