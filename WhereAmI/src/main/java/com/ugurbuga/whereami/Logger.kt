package com.ugurbuga.whereami

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.fragment.app.Fragment

internal object Logger {

    private const val WHERE_AM_I = "WhereAmI"

    fun logActivity(activity: Activity, logEnabled: Boolean) {
        val activityName = getFormattedName(activity.javaClass)
        if (logEnabled) {
            Log.d(WHERE_AM_I, activityName)
        }
    }

    fun logFragment(activity: Activity, fragment: Fragment, logEnabled: Boolean) {
        if (fragment.tag !in ignoreList &&
            !(fragment.toString().startsWith(NAV_HOST_FRAGMENT) ||
                    fragment.toString().startsWith(SUPPORT_MAP_FRAGMENT))
        ) {
            val activityFragmentName =
                activity.javaClass.simpleName.plus(" - ").plus(getFormattedName(fragment.javaClass))
            if (logEnabled) {
                Log.d(WHERE_AM_I, activityFragmentName)
            }
        }
    }

    private const val NAV_HOST_FRAGMENT = "NavHostFragment"
    private const val SUPPORT_MAP_FRAGMENT = "SupportMapFragment"
    private const val GLIDE_MANAGER = "com.bumptech.glide.manager"
    private const val SUPPORT_LIFECYCLE_FRAGMENT_IMPL = "SupportLifecycleFragmentImpl"

    private val ignoreList = arrayListOf(GLIDE_MANAGER, SUPPORT_LIFECYCLE_FRAGMENT_IMPL)

    private fun getFormattedName(clazz: Class<Any>): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (clazz.getDeclaredAnnotation(Metadata::class.java) != null) {
                "(" + clazz.simpleName + ".kt:1)"
            } else {
                "(" + clazz.simpleName + ".java:1)"
            }
        } else {
            clazz.simpleName
        }
    }
}