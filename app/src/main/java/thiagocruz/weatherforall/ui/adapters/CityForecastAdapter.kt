package thiagocruz.weatherforall.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_city_forecast.view.*
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.R
import thiagocruz.weatherforall.entities.CityForecast

class CityForecastAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mList: List<CityForecast>? = null
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
        mList?.get(position)?.let { cityForecastItemViewHolder.onBindViewHolder(it) }
    }

    class CityForecastItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBindViewHolder(cityForecast: CityForecast) {
            itemView.textViewCityName.text = cityForecast.name

            Picasso.get()
                    .load(cityForecast.weather.first().getWheaterImageUrl())
                    .placeholder(R.mipmap.ic_launcher_foreground)
                    .error(R.mipmap.ic_launcher_foreground)
                    .into(itemView.imageViewWeatherIcon)

            itemView.textViewWeatherDescription.text = cityForecast.weather.first().weatherDescription

            itemView.textViewMainTemp.text = cityForecast.temperature.averageTemp.toString()

            itemView.textViewMinTemp.text = cityForecast.temperature.minimumTemp.toString()

            itemView.textViewMaxTemp.text = cityForecast.temperature.maximumTemp.toString()
        }
    }

    class EmptyItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
