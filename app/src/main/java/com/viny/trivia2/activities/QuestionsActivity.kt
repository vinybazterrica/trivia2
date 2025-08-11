package com.viny.trivia2.activities

import android.os.Bundle
import android.os.CountDownTimer
import android.view.ContextThemeWrapper
import android.view.View
import android.view.View.GONE
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.viny.trivia2.BuildConfig
import com.viny.trivia2.R
import com.viny.trivia2.databinding.ActivityQuestionsBinding
import com.viny.trivia2.helper.DialogHelper
import com.viny.trivia2.helper.ResourcesHelper
import com.viny.trivia2.helper.StorageHelper
import com.viny.trivia2.network.apis.QuestionsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class QuestionsActivity : AppCompatActivity() {

    lateinit var binding: ActivityQuestionsBinding

    var playerScore: Int = 0
    var maxPage: Int = Random.nextInt(100)

    private var countDownTimer: CountDownTimer? = null
    private val totalTime = 20_000L // 20 segundos
    private val interval = 100L     // actualizamos cada 100ms

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showMaxScore()
        getQuestion()
    }

    private fun getQuestion() {
        if (!ResourcesHelper.hasInternetConnection(this)) {
            Toast.makeText(this, getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val questionsResponse = QuestionsApi.instance.getQuestions(
                    apiKey = BuildConfig.API_KEY,
                    limit = 1,
                    page = maxPage
                )

                val questions = questionsResponse.questions
                if (questions.isNotEmpty()) {
                    var question = questions.firstOrNull()

                    maxPage = Random.nextInt(questionsResponse.total)

                    var answersListRandom =
                        createAnswersList(question!!.correctAnswers, question.incorrectAnswers)

                    withContext(Dispatchers.Main) {
                        questions.let {
                            setAnswers(
                                answers = answersListRandom,
                                correct = question.correctAnswers
                            )

                            setQuestion(question.question)
                        }
                    }

                } else {
                    Toast.makeText(
                        this@QuestionsActivity,
                        getString(R.string.no_questions_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@QuestionsActivity, "Error: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setQuestion(question: String) {
        binding.progressBar.visibility = GONE
        binding.txtPregunta.text = question
        binding.linearQuestions.visibility = Button.VISIBLE
        startTimer()
    }

    private fun createAnswersList(correctAnswer: String, answers: List<String>): List<String> {
        val allAnswers = mutableListOf<String>().apply {
            add(correctAnswer)
            addAll(answers)
        }.shuffled()

        return allAnswers
    }

    private fun setAnswers(answers: List<String>, correct: String) {
        binding.linearRespuestas.removeAllViews()

        answers.forEach { answerText ->
            val button = MaterialButton(
                ContextThemeWrapper(this, R.style.ButtonsBorder),
                null,
                0
            ).apply {
                text = answerText
                setOnClickListener {
                    canceltimer()
                    if (answerText == correct) {
                        Toast.makeText(context, getString(R.string.correct), Toast.LENGTH_SHORT).show()
                        setScore()
                        getQuestion()
                    } else {
                        Toast.makeText(context, getString(R.string.incorrect), Toast.LENGTH_SHORT).show()
                        DialogHelper.showIncorrectDialog(
                            this@QuestionsActivity,
                            selectErrorMessage(),
                            onAccept = { finish() }
                        )
                    }
                }
            }
            binding.linearRespuestas.addView(button)
        }
    }

    private fun setScore() {
        playerScore += 1
        setMaxScore(playerScore)
        binding.tvActualScore.text = playerScore.toString()
    }

    private fun setMaxScore(actualScore: Int) {
        if (actualScore > StorageHelper.getMaxScore(this)) {
            StorageHelper.saveMaxScore(this, actualScore)
        }
        showMaxScore()
    }

    private fun showMaxScore(){
        binding.tvMaxScore.text = StorageHelper.getMaxScore(this).toString()
    }

    private fun selectErrorMessage() : String{
        return when{
            playerScore <= 10 -> getString(R.string.asshole_message, playerScore)
            playerScore in 11..30 -> getString(R.string.regular_score, playerScore)
            else -> getString(R.string.good_score, playerScore)
        } as String
    }

    private fun startTimer() {
        canceltimer()

        binding.progressTimer.visibility = View.VISIBLE
        binding.progressTimer.progress = 100

        countDownTimer = object : CountDownTimer(totalTime, interval) {
            override fun onTick(millisUntilFinished: Long) {
                val totalSeconds = (millisUntilFinished / 1000).toInt()
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60
                binding.tvTimer.text = String.format("%02d:%02d", minutes, seconds)

                val progress = (millisUntilFinished / totalTime.toFloat()) * 100
                binding.progressTimer.progress = progress.toInt()
            }

            override fun onFinish() {
                binding.tvTimer.text = "00:00"
                binding.progressTimer.progress = 0
                DialogHelper.showIncorrectDialog(
                    this@QuestionsActivity,
                    getString(R.string.timeout_message, playerScore),
                    onAccept = { finish() }
                )
            }
        }.start()
    }

    private fun canceltimer(){
        countDownTimer?.cancel()
    }
}