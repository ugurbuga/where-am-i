package com.ugurbuga.whereami

import android.app.Activity
import android.os.Build
import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment


internal object Extensions {

    fun String.fromHtml(): Spanned {
        return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    val Any.simpleName: String
        get() = javaClass.simpleName


    val Any.formattedName: String
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

}
