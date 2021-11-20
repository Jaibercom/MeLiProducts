package com.jaiberyepes.mercadolibre.presentation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jaiberyepes.mercadolibre.R
import kotlinx.coroutines.*

/**
 * Activity for the Splash Entry-Point.
 *
 * @author jaiber.yepes
 */
class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        activityScope.launch {
            delay(2000)

            with(Intent(this@SplashActivity, MainActivity::class.java)) {
                startActivity(this)
            }
            finish()
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}
