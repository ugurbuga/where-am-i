package com.ugurbuga.whereami

import android.app.Activity
import androidx.fragment.app.Fragment
import com.ugurbuga.whereami.Const.GLIDE_MANAGER
import com.ugurbuga.whereami.Const.NAV_HOST_FRAGMENT
import com.ugurbuga.whereami.Const.SUPPORT_LIFECYCLE_FRAGMENT_IMPL
import com.ugurbuga.whereami.Const.SUPPORT_MAP_FRAGMENT
import com.ugurbuga.whereami.Extensions.formattedName
import com.ugurbuga.whereami.Extensions.simpleName

internal object Logger {

    private val ignoreList = arrayListOf(GLIDE_MANAGER, SUPPORT_LIFECYCLE_FRAGMENT_IMPL)

    fun logActivity(activity: Activity) {
        Logcat.log(activity.formattedName)
        PushNotification.send(activity, activity.simpleName)
        Toast.show(activity, activity.simpleName)
    }

    fun logFragment(activity: Activity, fragment: Fragment) {
        if (fragment.tag !in ignoreList &&
            !(fragment.toString().startsWith(NAV_HOST_FRAGMENT) ||
                    fragment.toString().startsWith(SUPPORT_MAP_FRAGMENT))
        ) {
            Logcat.log(activity, fragment)
            PushNotification.send(activity, fragment.simpleName)
            Toast.show(activity, fragment.simpleName)
        }
    }

    fun setEnabled(waiConfig: WAIConfig) {
        Logcat.setEnabled(waiConfig.consoleLogEnabled)
        Logcat.setLogLevel(waiConfig.consoleLogLevel)
        PushNotification.setEnabled(waiConfig.pushEnabled)
        Toast.setEnabled(waiConfig.toastEnabled)
    }

}