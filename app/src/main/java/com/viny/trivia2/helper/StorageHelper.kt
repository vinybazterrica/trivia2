package com.viny.trivia2.helper

import android.content.Context
import com.viny.trivia2.utils.Constants

object StorageHelper {

    private const val PREFS_NAME = "trivia_prefs"

    private fun getPrefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveMaxScore(context: Context, score: Int) {
        getPrefs(context).edit().putInt(Constants.KEY_MAX_SCORE, score).apply()
    }

    fun getMaxScore(context: Context): Int {
        return getPrefs(context).getInt(Constants.KEY_MAX_SCORE, 0)
    }

}