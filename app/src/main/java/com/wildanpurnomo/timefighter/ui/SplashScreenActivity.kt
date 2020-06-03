package com.wildanpurnomo.timefighter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.wildanpurnomo.timefighter.R
import com.wildanpurnomo.timefighter.ui.auth.AuthActivity

class SplashScreenActivity : AppCompatActivity() {

    private val splashTime = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this, AuthActivity::class.java))
        }, splashTime)
    }
}
