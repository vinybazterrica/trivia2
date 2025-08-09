package com.viny.trivia2.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showMaxScore()
        getQuestion()
    }

    private fun getQuestion() {
        if (!ResourcesHelper.hasInternetConnection(this)) {
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_SHORT).show()
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
                        "No se encontraron preguntas",
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
            val button = android.widget.Button(this@QuestionsActivity).apply {
                text = answerText
                setOnClickListener {
                    if (answerText == correct) {
                        Toast.makeText(context, "¡Correcto!", Toast.LENGTH_SHORT).show()
                        setScore()
                        getQuestion()
                    } else {
                        Toast.makeText(context, "Incorrecto", Toast.LENGTH_SHORT).show()
                        DialogHelper.showIncorrectDialog(
                            this@QuestionsActivity,
                            playerScore,
                            onAccept = {
                                finish()
                            })
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

    private fun resetScore() {
        playerScore = 0
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
}