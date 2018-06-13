package thiagocruz.weatherforall.ui.activities

import android.os.Bundle
import android.app.Activity
import android.content.Intent
import thiagocruz.weatherforall.R
import thiagocruz.weatherforall.utils.DelayUtil

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        DelayUtil.standardDelay {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
