package com.example.quizapp.api

import com.example.quizapp.model.MyModel
import retrofit2.Response
import retrofit2.http.GET

interface QuestionsAPI {

    @GET("whast.php")
    suspend fun getQuestions(): Response<MyModel>

}