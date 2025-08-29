package com.viny.trivia2.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.viny.trivia2.databinding.ActivityMainBinding
import com.viny.trivia2.helper.StorageHelper
import com.viny.trivia2.utils.Constants

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        StorageHelper.init(this)
        logFirebaseToken()
        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL,
                "Notificaciones de Firebase",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Estas notifiaciones son de Firebase!"

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun logFirebaseToken(){
        Firebase.messaging.token.addOnCompleteListener {
            if (it.isSuccessful){
                Log.d("TOKEN FIREBASE", it.result)
            }
        }
    }
}