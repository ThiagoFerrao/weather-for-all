package thiagocruz.weatherforall.ui.activities

import android.content.Intent
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import thiagocruz.weatherforall.Constant
import thiagocruz.weatherforall.R
import thiagocruz.weatherforall.managers.TemperatureUnitManager
import thiagocruz.weatherforall.ui.adapters.CityForecastAdapter

class MainActivity : BaseActivity() {

    private var mAdapter: CityForecastAdapter? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val itemChangeMetrics = menu?.findItem(R.id.action_change_metrics)
        TemperatureUnitManager.setCurrentMenuItemIcon(this, itemChangeMetrics)
        itemChangeMetrics?.setOnMenuItemClickListener { item ->
            mPresenter?.handleChangeMetrics(item)
            true
        }

        val itemChangeToMap = menu?.findItem(R.id.action_change_to_map)
        itemChangeToMap?.setOnMenuItemClickListener { _ ->
            mPresenter?.handleChangeScreen()
            true
        }

        return true
    }


    // MARK: BaseActivity Methods

    override fun getActivityLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun getActivityToolbar(): Toolbar {
        return toolbar
    }

    override fun setupContent() {
        floatingButtonGetLocation.setOnClickListener { _ -> mPresenter?.getUserLocation() }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()

        mPresenter?.initializeWithIntent(intent)
    }

    override fun cityForecastListWasUpdated() {
        if (recyclerView.adapter != null) {
            mAdapter?.setCityForecastList(mList!!)
            mAdapter?.notifyDataSetChanged()
            return
        }

        mAdapter = CityForecastAdapter(this)
        mAdapter?.setCityForecastList(mList!!)
        recyclerView.adapter = mAdapter
    }

    override fun presentNextScreen() {
        val intent = Intent(this, MapActivity::class.java)
        mList?.let { intent.putParcelableArrayListExtra(Constant.IntentExtra.CITY_FORECAST_LIST, ArrayList(it)) }
        startActivity(intent)
        finish()
    }
}
