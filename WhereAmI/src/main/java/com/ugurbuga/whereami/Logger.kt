package com.ugurbuga.whereami

import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.fragment.app.Fragment

internal object Logger {

    private const val WHERE_AM_I = "WhereAmI"
    private const val DASH_SPACE = " - "

    fun logActivity(activity: Activity) {
        val activityName = activity.formattedName
        Log.d(WHERE_AM_I, activityName)
    }

    fun logFragment(activity: Activity, fragment: Fragment) {
        if (fragment.tag !in ignoreList &&
            !(fragment.toString().startsWith(NAV_HOST_FRAGMENT) ||
                    fragment.toString().startsWith(SUPPORT_MAP_FRAGMENT))
        ) {
            val allParentFragmentName = getAllParentFragmentName(fragment.parentFragment)

            val fragments = allParentFragmentName.plus(DASH_SPACE).plus(fragment.formattedName)

            Log.d(WHERE_AM_I, activity.simpleName.plus(fragments))
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

private val Any.simpleName: String
    get() = javaClass.simpleName


private val Any.formattedName: String
    get() = if (this is Activity || this is Fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (javaClass.getDeclaredAnnotation(Metadata::class.java) != null) {
                "($simpleName.kt:1)"
            } else {
                "($simpleName.java:1)"
            }
        } else {
            simpleName
        }
    } else {
        ""
    }