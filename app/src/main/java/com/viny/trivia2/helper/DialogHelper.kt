package com.viny.trivia2.helper

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.viny.trivia2.R

object DialogHelper {

    private var currentDialog: AlertDialog? = null

    fun showIncorrectDialog(activity: Activity, message: String, onAccept: () -> Unit) {
        if (currentDialog?.isShowing == true) return

        currentDialog = MaterialAlertDialogBuilder(activity)
            .setIcon(R.drawable.ic_error)
            .setTitle(activity.getString(R.string.incorrect_answer_title))
            .setMessage(message)
            .setPositiveButton(activity.getString(R.string.accept)) { dialog, which ->
                activity.finish()
            }
            .create()

        currentDialog?.show()
    }

    fun showTimeOutDialog(activity: Activity, message: String, onAccept: () -> Unit) {
        if (currentDialog?.isShowing == true) return

        currentDialog = MaterialAlertDialogBuilder(activity)
            .setIcon(R.drawable.ic_timeout)
            .setTitle(activity.getString(R.string.incorrect_answer_title))
            .setMessage(message)
            .setPositiveButton(activity.getString(R.string.accept)) { dialog, which ->
                activity.finish()
            }
            .create()

        currentDialog?.show()
    }

    fun showNoInternetDialog(activity: Activity, onRetry: () -> Unit) {
        if (currentDialog?.isShowing == true) return

        currentDialog = MaterialAlertDialogBuilder(activity)
            .setIcon(R.drawable.ic_no_internet)
            .setTitle(activity.getString(R.string.no_internet_title))
            .setMessage(activity.getString(R.string.no_internet_description))
            .setPositiveButton(activity.getString(R.string.retry)) { dialog, _ ->
                dialog.dismiss()
                currentDialog = null
                onRetry()
            }
            .setNegativeButton(activity.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
                currentDialog = null
                activity.finishAffinity()
            }
            .setCancelable(false)
            .create()

        currentDialog?.show()
    }
}
