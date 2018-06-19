package thiagocruz.weatherforall.managers

import android.content.Context
import android.support.test.InstrumentationRegistry
import org.junit.Before
import org.junit.Test
import thiagocruz.weatherforall.Constant

class TemperatureUnitManagerTest {

    private val testContext = InstrumentationRegistry.getContext()

    @Before
    fun setup() {
        val editor = testContext.getSharedPreferences(Constant.SharedPreferences.DEFAULT, Context.MODE_PRIVATE).edit()
        editor?.putString(Constant.SharedPreferences.KEY_TEMPERATURE_UNIT, Constant.TemperatureUnit.CELSIUS)
    }

    @Test
    fun testUpdateTemperatureUnit() {
        TemperatureUnitManager.updateTemperatureUnit(testContext, null)
        val preferences = testContext.getSharedPreferences(Constant.SharedPreferences.DEFAULT, Context.MODE_PRIVATE)
        val testTempUnit = preferences.getString(Constant.SharedPreferences.KEY_TEMPERATURE_UNIT, Constant.TemperatureUnit.CELSIUS)
        val expectTempUnit = Constant.TemperatureUnit.FAHRENHEIT

        assert(testTempUnit == expectTempUnit)
    }

    @Test
    fun testGetCurrentTemperatureUnit() {
        val testCurrentTempUnit = TemperatureUnitManager.getCurrentTemperatureUnit(testContext)
        val expectCurrentTempUnit = Constant.TemperatureUnit.CELSIUS

        assert(testCurrentTempUnit == expectCurrentTempUnit)
    }

    @Test
    fun testIsTemperatureUnitCelsius() {
        assert(TemperatureUnitManager.isTemperatureUnitCelsius(Constant.TemperatureUnit.CELSIUS))
        assert(!TemperatureUnitManager.isTemperatureUnitCelsius(Constant.TemperatureUnit.FAHRENHEIT))
    }

    @Test
    fun testGetTemperatureUnitSuffix() {
        val expectTempUnitSuffix = "°C"
        val testTempUnitSuffix = TemperatureUnitManager.getTemperatureUnitSuffix(testContext)

        assert(expectTempUnitSuffix == testTempUnitSuffix)
    }
}
