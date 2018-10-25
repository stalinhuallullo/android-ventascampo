package dev.lstr.llevateclaro.data.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dev.lstr.llevateclaro.data.datasource.pref.CurrentUser
import dev.lstr.llevateclaro.presentation.util.TrackerGA

/**
 * Created by lesternr on 5/6/18.
 */

class MyFirebaseMessagingService: FirebaseMessagingService() {

    val TAG = "MyFirebaseMessaging"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.from)

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            val title = remoteMessage.data["title"]
            val message = remoteMessage.data["message"]


            showNotification(title, message)

            Log.d(TAG, "Title data payload: " + title)
            Log.d(TAG, "Message data payload: " + message)

            TrackerGA.getInstance(this).registerEvent("Notification", "Updates", "$title")

//            Log.d(TAG, "Message data payload: " + data["message"])

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
//                scheduleJob()
            } else {
                // Handle message within 10 seconds
//                handleNow()
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body)
        }
    }

    private fun showNotification(title: String?, message: String?) {
        val notificationReceiver = NotificationReceiver()

        notificationReceiver.showNotification(applicationContext, title!!, message!!)
    }

    override fun onNewToken(token: String?) {
        val currUser = CurrentUser.getInstance(this)
        currUser.saveToken(token!!)
    }
}