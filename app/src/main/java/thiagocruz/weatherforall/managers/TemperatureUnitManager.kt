package thiagocruz.weatherforall.managers

import android.content.Context
import android.view.MenuItem
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.R

object TemperatureUnitManager {

    fun updateTemperatureUnit(context: Context?, menuItem: MenuItem?) {
        val preferences = context?.getSharedPreferences(Constant.SharedPreferences.DEFAULT, Context.MODE_PRIVATE)
        val editor = preferences?.edit()

        val currentTemperatureUnit = context?.let { getCurrentTemperatureUnit(it) }

        if (isTemperatureUnitCelsius(currentTemperatureUnit)) {
            editor?.putString(Constant.SharedPreferences.KEY_TEMPERATURE_UNIT, Constant.TemperatureUnit.FAHRENHEIT)
            menuItem?.icon = context?.getDrawable(R.drawable.ic_temp_fahrenheit)

        } else {
            editor?.putString(Constant.SharedPreferences.KEY_TEMPERATURE_UNIT, Constant.TemperatureUnit.CELSIUS)
            menuItem?.icon = context?.getDrawable(R.drawable.ic_temp_celsius)
        }

        editor?.apply()
    }

    fun getCurrentTemperatureUnit(context: Context): String {
        val preferences = context.getSharedPreferences(Constant.SharedPreferences.DEFAULT, Context.MODE_PRIVATE)
        return preferences.getString(Constant.SharedPreferences.KEY_TEMPERATURE_UNIT, Constant.TemperatureUnit.CELSIUS)
    }

    fun setCurrentMenuItemIcon(context: Context, menuItem: MenuItem?) {
        if (isTemperatureUnitCelsius(getCurrentTemperatureUnit(context))) {
            menuItem?.icon = context.getDrawable(R.drawable.ic_temp_celsius)
            return
        }

        menuItem?.icon = context.getDrawable(R.drawable.ic_temp_fahrenheit)
    }

    fun isTemperatureUnitCelsius(tempUnit: String?): Boolean {
        if (tempUnit == null) return true

        return tempUnit == Constant.TemperatureUnit.CELSIUS
    }

    fun getTemperatureUnitSuffix(context: Context): String {
        val tempUnit = TemperatureUnitManager.getCurrentTemperatureUnit(context)
        if (TemperatureUnitManager.isTemperatureUnitCelsius(tempUnit)) {
            return "°C"
        }

        return "°F"
    }
}
