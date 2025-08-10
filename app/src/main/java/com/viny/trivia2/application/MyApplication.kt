package com.viny.trivia2.application

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.viny.trivia2.databinding.ActivityMainBinding

class MyApplication : Application()  {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}