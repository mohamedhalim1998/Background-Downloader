package com.mohamed.halim.essa.backgroundchanger.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.mohamed.halim.essa.backgroundchanger.BuildConfig
import com.mohamed.halim.essa.backgroundchanger.R
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}
