package dev.lstr.llevateclaro.data.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import dev.lstr.llevateclaro.R
import java.util.*
import dev.lstr.llevateclaro.presentation.ui.activity.HomeActivity
import android.content.Intent
import dev.lstr.llevateclaro.presentation.ui.activity.SplashActivity


class NotificationReceiver{
    var context: Context? = null
    val CHANNEL_ID = "LLEVATELO_CHANNEL"
    var notificationManager:NotificationManager? = null

    fun showNotification(context:Context, title: String, message: String){
        this.context = context
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        buildNotification(title, message)
    }


    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ChannelName"
            val description = "ChannelDesc"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            notificationManager!!.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(title: String, message: String){
        val notificationIntent = Intent(context, SplashActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        val mBuilder = NotificationCompat.Builder(context!!, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notifId = Date().time.toInt()
        notificationManager!!.notify(notifId, mBuilder.build())
    }
}