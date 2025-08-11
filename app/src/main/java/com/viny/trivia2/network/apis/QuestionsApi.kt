package com.viny.trivia2.network.apis

import com.viny.trivia2.models.QuestionsResponse
import com.viny.trivia2.network.Services
import com.viny.trivia2.utils.Constants
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface QuestionsApi {

    companion object {
        val instance = Retrofit.Builder().baseUrl(Services.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionsApi::class.java)
    }

    @GET(Services.ENDPOINT_QUESTIONS)
    suspend fun getQuestions(
        @Header(Services.AUTHORIZATION) apiKey: String,
        @Query(Services.LIMIT) limit: Int,
        @Query(Services.PAGE) page: Int
    ): QuestionsResponse

}