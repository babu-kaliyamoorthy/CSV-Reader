package com.rabobank.csvreader.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.rabobank.csvreader.R

/**
 * Created by Babu Kaliyamoorthy on 23/11/19.
 */
class SplashScreenActivity : AppCompatActivity() {

    /**time out seconds to show the splash screen
    */
    private val splashTimeOut: Long = 2000 // 2 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, IssuesListActivity::class.java))
            finish()
        }, splashTimeOut)
    }
}