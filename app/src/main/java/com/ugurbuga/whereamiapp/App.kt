package com.ugurbuga.whereamiapp

import android.app.Application
import com.ugurbuga.whereami.WhereAmI

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        WhereAmI.init(application = this, pushEnabled = true, logEnabled = true)
    }
}