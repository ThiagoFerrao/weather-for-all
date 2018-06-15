package thiagocruz.weatherforall

object Constant {

    object AppLanguage {
        const val PORTUGUESE = "pt"
    }

    object PermissionRequestCode {
        const val GEOLOCATION = 160
    }

    object ActivityResultRequestCode {
        const val GEOLOCATION = 80
    }

    object CityForecastAdapter {
        const val EMPTY_ITEM_VIEW_TYPE = 0
        const val CONTENT_ITEM_VIEW_TYPE = 1
    }

    object TemperatureUnit {
        const val CELSIUS = "metric"
        const val FAHRENHEIT = "imperial"
    }

    object SharedPreferences {
        const val DEFAULT = "AppSharedPreferences"
        const val KEY_TEMPERATURE_UNIT = "keyTemperatureUnit"
        const val KEY_USER_LOCATION_LATITUDE = "keyUserLocationLatitude"
        const val KEY_USER_LOCATION_LONGITUDE = "keyUserLocationLongitude"
    }

    object IntentExtra {
        const val CITY_FORECAST_LIST = "ExtraCityForecastList"
    }

    object Map {
        const val DEFAULT_ZOOM = 9f
    }

    object ViewAlpha {
        const val DISABLE = 0.5f
        const val ENABLE = 1f
    }
}
