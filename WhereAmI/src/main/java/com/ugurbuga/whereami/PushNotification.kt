package com.ugurbuga.whereami

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.text.Spanned
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ugurbuga.whereami.Const.COLOR
import com.ugurbuga.whereami.Const.NAME
import com.ugurbuga.whereami.Extensions.fromHtml

internal object PushNotification {

    private var enabled = false
    private var list = arrayListOf<String>()
    private var count = 0

    private fun addAndFormatPages(text: String): Spanned {
        count = 0
        list.add(text)
        list = ArrayList(list.takeLast(10))
        return list.reversed().joinToString("<br>") { getFormatted(it) }.fromHtml()
    }

    private fun getFormatted(it: String): CharSequence {
        return if (count++ % 2 == 0) {
            "<font color='${COLOR}'>$it</font>"
        } else {
            it
        }
    }

    fun send(context: Context, text: String) {
        if (!enabled) {
            return
        }
        val pages = addAndFormatPages(text)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            val existingChannel = notificationManager.getNotificationChannel(NAME)
            if (existingChannel == null) {
                val channel =
                    NotificationChannel(NAME, NAME, NotificationManager.IMPORTANCE_LOW)
                channel.description = NAME

                notificationManager.createNotificationChannel(channel)
            }
        }

        val builder = NotificationCompat.Builder(context, NAME)
        builder.setContentTitle(NAME)
            .setContentText(pages)
            .setColor(Color.parseColor(COLOR))
            .setSmallIcon(R.drawable.ic_location)
            .setStyle(NotificationCompat.BigTextStyle().bigText(pages))
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setAutoCancel(true)
            .setNotificationSilent()

        notify(context, builder)
    }

    private fun notify(context: Context, builder: NotificationCompat.Builder) {
        val notification = builder.build()
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(Int.MAX_VALUE, notification)

        }
    }

    fun setEnabled(enabled: Boolean) {
        this.enabled = enabled
    }

}