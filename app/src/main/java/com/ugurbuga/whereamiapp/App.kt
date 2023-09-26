package com.ugurbuga.whereamiapp

import android.app.Application
import com.ugurbuga.whereami.WAIConfig
import com.ugurbuga.whereami.WAILogLevel
import com.ugurbuga.whereami.WhereAmI

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        WhereAmI.init(
            application = this,
            WAIConfig(
                consoleLogEnabled = true,
                pushEnabled = true,
                toastEnabled = true,
                consoleLogLevel = WAILogLevel.EXTENDED
            )
        )
    }
}