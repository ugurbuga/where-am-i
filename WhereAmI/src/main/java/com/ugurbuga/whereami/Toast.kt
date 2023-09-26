package com.ugurbuga.whereami

import android.content.Context
import android.widget.Toast

internal object Toast {

    private var toast: Toast? = null
    private var enabled: Boolean = false

    fun show(context: Context, message: String) {
        toast?.cancel()
        if (enabled) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toast?.show()
        }
    }

    fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

}