package com.viny.trivia2.utils

import com.viny.trivia2.BuildConfig

data object Constants {

    //Version code
    const val VERSION_CODE = "Versión ${BuildConfig.VERSION_NAME}"

    //STORAGE
    const val KEY_MAX_SCORE = "KEY_MAX_SCORE"

    //Range of Score
    const val MIN_SCORE_ASSHOLE = 10
    const val MIN_SCORE_REGULAR = 30


    //Timer
    const val TOTAL_TIMER = 20_000L
    const val END_TIMER = "00:00"
    const val INTERVAL_TIMER = 100L

    //Permission
    const val REQUEST_NOTIFICATION_PERMISSION = 1001

    //Notifications
    const val NOTIFICATION_CHANNEL = "NOTIFICATION_CHANNEL"
}