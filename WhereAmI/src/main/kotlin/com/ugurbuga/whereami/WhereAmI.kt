package com.ugurbuga.whereami

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

object WhereAmI {

    private var fragmentCallback: FragmentManager.FragmentLifecycleCallbacks? = null
    private var activityCallback: Application.ActivityLifecycleCallbacks? = null

    fun init(application: Application, logEnabled: Boolean) {

        application.unregisterActivityLifecycleCallbacks(activityCallback)

        activityCallback = object : Application.ActivityLifecycleCallbacks {

            override fun onActivityResumed(activity: Activity) {
                Logger.logActivity(activity, logEnabled)
                registerFragmentLifecycleCallbacks(activity, logEnabled)
            }

            override fun onActivityPaused(activity: Activity) {
                unregisterFragmentLifecycleCallbacks(activity)
            }

            override fun onActivityStarted(activity: Activity) {}

            override fun onActivityDestroyed(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
        }

        application.registerActivityLifecycleCallbacks(activityCallback)
    }

    private fun registerFragmentLifecycleCallbacks(activity: Activity, logEnabled: Boolean) {
        if (activity is FragmentActivity) {
            val fragmentManager = activity.supportFragmentManager
            fragmentCallback = object : FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentResumed(fm: FragmentManager, fragment: Fragment) {
                    super.onFragmentResumed(fm, fragment)
                    Logger.logFragment(activity, fragment, logEnabled)
                }
            }
            fragmentManager.registerFragmentLifecycleCallbacks(
                fragmentCallback as FragmentManager.FragmentLifecycleCallbacks,
                true
            )
        }
    }

    private fun unregisterFragmentLifecycleCallbacks(activity: Activity) {
        if (activity is FragmentActivity) {
            val fragmentManager = activity.supportFragmentManager
            if (fragmentCallback != null) {
                fragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallback as FragmentManager.FragmentLifecycleCallbacks)
            }
        }
    }
}