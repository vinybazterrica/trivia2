package com.viny.trivia2.helper

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.viny.trivia2.activities.MainActivity
import com.viny.trivia2.activities.QuestionsActivity

object IntentHelper {

    private fun launchIntentAndFinish(
        activity: Activity,
        clazz: Class<*>,
        params: Bundle?,
        finish: Boolean
    ) {
        val intent = Intent(activity, clazz)
        params?.let {
            intent.putExtras(it)
        }
        activity.startActivity(intent)
        if (finish) {
            activity.finish()
        }
    }

    fun goToMain(activity: Activity, bundle: Bundle? = null) {
        launchIntentAndFinish(activity, MainActivity::class.java, bundle, true)
    }

    fun goToQuestions(activity: Activity, bundle: Bundle? = null) {
        launchIntentAndFinish(activity, QuestionsActivity::class.java, bundle, false)
    }

    fun goToUrl(activity: Activity, url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = android.net.Uri.parse(url)
        activity.startActivity(intent)
    }
}