package thiagocruz.weatherforall.ui.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import thiagocruz.weatherforall.R
import thiagocruz.weatherforall.presenters.MainPresenter
import thiagocruz.weatherforall.presenters.MainPresenterImpl
import thiagocruz.weatherforall.views.MainView

class MainActivity : AppCompatActivity(), MainView {

    var mEventHandler: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        mEventHandler = MainPresenterImpl()
        mEventHandler?.attachView(this)
    }
}
