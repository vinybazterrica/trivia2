package com.viny.trivia2.models.db

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.viny.trivia2.models.Question

@DatabaseTable(tableName = "questions")
data class QuestionEntity(
    @DatabaseField(id = true)
    var id: String = "",

    @DatabaseField
    var category: String = "",

    @DatabaseField
    var format: String = "",

    @DatabaseField
    var question: String = "",

    @DatabaseField
    var correctAnswers: String = "",

    @DatabaseField
    var incorrectAnswersJson: String = ""
) {
    fun getQuestionDAO(): Question {
        return Question(
            id,
            category,
            format,
            question,
            correctAnswers,
            incorrectAnswersJson.split("|")
        )
    }

    companion object {
        fun setQuestionDAO(q: Question): QuestionEntity {
            return QuestionEntity(
                q.id,
                q.category,
                q.format,
                q.question,
                q.correctAnswers,
                q.incorrectAnswers.joinToString("|")
            )
        }
    }
}