package com.ugurbuga.whereami

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

object WhereAmI {
    fun init(application: Application, logEnabled: Boolean) {
        if (!logEnabled) {
            return
        }

        var fragmentCallback: FragmentManager.FragmentLifecycleCallbacks? = null

        val activityCallback = object : Application.ActivityLifecycleCallbacks {

            override fun onActivityResumed(activity: Activity) {
                Logger.logActivity(activity)
                fragmentCallback = registerFragmentLifecycleCallbacks(activity)
            }

            override fun onActivityPaused(activity: Activity) {
                unregisterFragmentLifecycleCallbacks(activity, fragmentCallback)
                fragmentCallback = null
            }

            override fun onActivityStarted(activity: Activity) {}

            override fun onActivityDestroyed(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
        }

        application.registerActivityLifecycleCallbacks(activityCallback)
    }

    private fun registerFragmentLifecycleCallbacks(activity: Activity): FragmentManager.FragmentLifecycleCallbacks? {
        if (activity !is FragmentActivity) return null

        val fragmentCallback = object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentResumed(fm: FragmentManager, fragment: Fragment) {
                super.onFragmentResumed(fm, fragment)
                Logger.logFragment(activity, fragment)
            }
        }

        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallback, true)

        return fragmentCallback
    }

    private fun unregisterFragmentLifecycleCallbacks(
        activity: Activity,
        fragmentCallback: FragmentManager.FragmentLifecycleCallbacks?
    ) {
        if (activity !is FragmentActivity || fragmentCallback == null) return

        activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallback)
    }
}