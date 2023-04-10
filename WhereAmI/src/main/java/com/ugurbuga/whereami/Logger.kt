package com.ugurbuga.whereami

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment

internal object Logger {

    private var lastActivity: String = ""
    private var lastFragment: String = ""
    private const val WHERE_AM_I = "WhereAmI"

    fun logActivity(activity: Activity, logEnabled: Boolean) {
        if (activity.toString() != lastActivity) {
            lastActivity = activity.toString()
            val activityName = activity.name()
            if (logEnabled) {
                Log.d(WHERE_AM_I, activityName)
            }
        }
    }

    fun logFragment(activity: Activity, fragment: Fragment, logEnabled: Boolean) {
        if (fragment.toString() != lastFragment &&
            fragment.tag !in ignoreList &&
            !fragment.toString().startsWith(NAV_HOST_FRAGMENT)
        ) {
            lastFragment = fragment.toString()
            val activityFragmentName = activity.name().plus(" - ").plus(fragment.name())
            if (logEnabled) {
                Log.d(WHERE_AM_I, activityFragmentName)
            }
        }
    }

    private const val NAV_HOST_FRAGMENT = "NavHostFragment"
    private const val GLIDE_MANAGER = "com.bumptech.glide.manager"
    private const val SUPPORT_LIFECYCLE_FRAGMENT_IMPL = "SupportLifecycleFragmentImpl"

    private val ignoreList = arrayListOf(GLIDE_MANAGER, SUPPORT_LIFECYCLE_FRAGMENT_IMPL)

    //private fun Activity.name(): String = "(${this.javaClass.simpleName}.kt:0)"
    //private fun Fragment.name(): String = "(${this.javaClass.simpleName}.kt:0)"

    private fun Activity.name(): String = this.javaClass.simpleName

    private fun Fragment.name(): String = this.javaClass.simpleName

}
