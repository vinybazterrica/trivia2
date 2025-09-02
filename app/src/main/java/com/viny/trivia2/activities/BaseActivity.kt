package com.viny.trivia2.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.viny.trivia2.BuildConfig
import com.viny.trivia2.helper.IntentHelper
import com.viny.trivia2.utils.Constants

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun showAppVersion(tvVersion: TextView?) {
        tvVersion?.text = Constants.VERSION_CODE
    }

    fun goToGithub() {
        IntentHelper.goToUrl(this, Constants.GITHUB_PROYECT_URL)
    }

    fun goToLinkedin() {
        IntentHelper.goToUrl(this, Constants.LINKEDIN_URL)
    }
}