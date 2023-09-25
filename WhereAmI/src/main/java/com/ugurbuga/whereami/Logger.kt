package com.ugurbuga.whereami

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment
import com.nextlua.mint.whereami.PushNotification
import com.ugurbuga.whereami.Extensions.formattedName
import com.ugurbuga.whereami.Extensions.simpleName

internal object Logger {

    private const val WHERE_AM_I = "WhereAmI"
    private const val DASH_SPACE = " - "

    fun logActivity(activity: Activity) {
        val activityName = activity.formattedName
        Log.d(WHERE_AM_I, activityName)
        PushNotification.send(activity, activity.simpleName)
    }

    fun logFragment(activity: Activity, fragment: Fragment) {
        if (fragment.tag !in ignoreList &&
            !(fragment.toString().startsWith(NAV_HOST_FRAGMENT) ||
                    fragment.toString().startsWith(SUPPORT_MAP_FRAGMENT))
        ) {
            val allParentFragmentName = getAllParentFragmentName(fragment.parentFragment)

            val fragments = allParentFragmentName.plus(DASH_SPACE).plus(fragment.formattedName)

            Log.d(WHERE_AM_I, activity.simpleName.plus(fragments))
            PushNotification.send(activity, fragment.simpleName)
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

    private const val NAV_HOST_FRAGMENT = "NavHostFragment"
    private const val SUPPORT_MAP_FRAGMENT = "SupportMapFragment"
    private const val GLIDE_MANAGER = "com.bumptech.glide.manager"
    private const val SUPPORT_LIFECYCLE_FRAGMENT_IMPL = "SupportLifecycleFragmentImpl"

    private val ignoreList = arrayListOf(GLIDE_MANAGER, SUPPORT_LIFECYCLE_FRAGMENT_IMPL)
}