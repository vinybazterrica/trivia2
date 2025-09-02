package com.viny.trivia2.helper

import android.content.Context
import androidx.compose.ui.unit.Constraints
import com.viny.trivia2.utils.Constants

object StorageHelper {

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private fun getPrefs() =
        appContext.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE)

    fun saveMaxScore(score: Int) {
        getPrefs().edit().putInt(Constants.KEY_MAX_SCORE, score).apply()
    }

    fun getMaxScore(): Int {
        return getPrefs().getInt(Constants.KEY_MAX_SCORE, 0)
    }

}