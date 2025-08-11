package com.viny.trivia2.network.firebase

import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.viny.trivia2.R
import com.viny.trivia2.utils.Constants

class FirebaseMessagingSerivice: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty() || remoteMessage.notification != null) {
            showNotification(remoteMessage)
        }
    }


    private fun showNotification(message: RemoteMessage){
        val notificationManager = getSystemService(NotificationManager::class.java)
        val notificacion = NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL)
            .setContentTitle(message.notification?.title)
            .setContentText(message.notification?.body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notificacion)
    }
}