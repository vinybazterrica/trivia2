package com.viny.trivia2.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.viny.trivia2.R
import com.viny.trivia2.databinding.ActivitySplashBinding
import com.viny.trivia2.db.DBQuestion
import com.viny.trivia2.db.QuestionsRepository
import com.viny.trivia2.helper.DialogHelper
import com.viny.trivia2.helper.IntentHelper
import com.viny.trivia2.helper.ResourcesHelper
import com.viny.trivia2.models.Question
import com.viny.trivia2.models.db.QuestionEntity
import com.viny.trivia2.utils.Constants
import kotlinx.coroutines.launch
import java.util.logging.Handler

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
                getQuestionsToOfflineLauncher()
            }
        } else {
            getQuestionsToOfflineLauncher()
        }
    }

    fun goToMain() {
        IntentHelper.goToMain(this)
    }

    fun getQuestionsToOfflineLauncher() {
        if (QuestionsRepository(this).hasQuestions()) {
            goToMain()
        } else {
            Toast.makeText(
                this,
                getString(R.string.message_getting_questions_offline),
                Toast.LENGTH_SHORT
            )
                .show()

            if (!ResourcesHelper.hasInternetConnection(this)) {
                DialogHelper.showNoInternetDialog(this) {
                    getQuestionsToOfflineLauncher()
                }
                return
            }

            lifecycleScope.launch {
                getQuestionsToOffline()
            }
        }
    }

    suspend fun getQuestionsToOffline() {

        val questionsResponse = getQuestion(184, this)

        val questionDao = DBQuestion(this).getQuestionDao()

        questionDao.deleteBuilder().delete()

        questionsResponse.questions.forEach { q ->
            val questionEntity = QuestionEntity.setQuestionDAO(q)
            questionDao.createOrUpdate(questionEntity)
        }

        goToMain()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_NOTIFICATION_PERMISSION) {
            // No importa si aceptó o no, seguimos
            getQuestionsToOfflineLauncher()
        }
    }
}