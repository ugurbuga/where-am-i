package com.ugurbuga.whereami

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment
import com.ugurbuga.whereami.Const.DASH_SPACE
import com.ugurbuga.whereami.Const.NAV_HOST_FRAGMENT
import com.ugurbuga.whereami.Const.WHERE_AM_I
import com.ugurbuga.whereami.Extensions.formattedName
import com.ugurbuga.whereami.Extensions.simpleName

internal object Console {

    private var enabled = false
    private var logLevel = WAILogLevel.BASIC

    fun log(log: String) {
        if (enabled) {
            Log.d(WHERE_AM_I, log)
        }
    }

    fun log(activity: Activity, fragment: Fragment) {
        when (logLevel) {
            WAILogLevel.BASIC -> {
                log(fragment.formattedName)
            }

            WAILogLevel.STANDARD -> {
                log(activity.simpleName.plus(DASH_SPACE).plus(fragment.formattedName))
            }

            WAILogLevel.EXTENDED -> {
                val allParentFragmentName = getAllParentFragmentName(fragment.parentFragment)
                val fragments = allParentFragmentName.plus(DASH_SPACE).plus(fragment.formattedName)
                log(activity.simpleName.plus(fragments))
            }
        }
    }

    private fun getAllParentFragmentName(fragment: Fragment?): String {
        return if (fragment != null) {
            if (fragment.toString().startsWith(NAV_HOST_FRAGMENT)) {
                getAllParentFragmentName(fragment.parentFragment)
            } else {
                getAllParentFragmentName(fragment.parentFragment) + DASH_SPACE + fragment.simpleName
            }
        } else {
            ""
        }
    }

    fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

    fun setLogLevel(logLevel: WAILogLevel) {
        this.logLevel = logLevel
    }

}