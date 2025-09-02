package com.viny.trivia2.activities

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.viny.trivia2.BuildConfig
import com.viny.trivia2.R
import com.viny.trivia2.helper.IntentHelper
import com.viny.trivia2.helper.ResourcesHelper
import com.viny.trivia2.models.Question
import com.viny.trivia2.models.QuestionsResponse
import com.viny.trivia2.network.apis.QuestionsApi
import com.viny.trivia2.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

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

    suspend fun getQuestion(limit: Int, context: Context): QuestionsResponse {
        return withContext(Dispatchers.IO) {
            try {
                val questionsResponse = QuestionsApi.instance.getQuestions(
                    apiKey = BuildConfig.API_KEY,
                    limit = limit,
                    page = 1
                )

                if (questionsResponse.questions.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.no_questions_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                questionsResponse
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
                throw e
            }
        }
    }
}