package com.viny.trivia2.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.viny.trivia2.R
import com.viny.trivia2.databinding.ActivitySplashBinding
import com.viny.trivia2.helper.IntentHelper
import com.viny.trivia2.utils.Constants

class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showAppVersion(binding.tvVersion)
        RequestPermission()
    }

    fun RequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    Constants.REQUEST_NOTIFICATION_PERMISSION
                )
            } else {
                IntentHelper.goToMain(this)
            }
        } else {
            IntentHelper.goToMain(this)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_NOTIFICATION_PERMISSION) {
            // No importa si aceptó o no, seguimos a MainActivity
            IntentHelper.goToMain(this)
        }
    }
}