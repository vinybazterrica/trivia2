package com.viny.trivia2.models

import com.google.gson.annotations.SerializedName

data class QuestionsResponse(
    @SerializedName("questions") val questions: List<Question>,
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("perPage") val perPage: Int
)

data class Question(
    val id: String,
    val category: String,
    val format: String,
    val question: String,
    val correctAnswers: String,
    val incorrectAnswers: List<String>
)
