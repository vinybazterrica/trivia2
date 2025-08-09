package com.viny.trivia2.helper

import android.app.Activity
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.widget.TextView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.viny.trivia2.R

object DialogHelper {

    fun showIncorrectDialog(activity: Activity, score: Int , onAccept: () -> Unit) {
        MaterialAlertDialogBuilder(activity)
            .setIcon(R.drawable.ic_error)
            .setTitle(activity.getString(R.string.incorrect_answer_title))
            .setMessage(activity.getString(R.string.incorrect_answer_message, score))
            .setPositiveButton(activity.getString(R.string.accept)) { dialog, which ->
               activity.finish()
            }
            .show()
    }


}