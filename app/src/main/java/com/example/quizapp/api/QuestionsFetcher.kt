package com.example.quizapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class QuestionsFetcher {

    val BASE_URL ="http://192.168.2.106/aasd/"


    fun getRetrofitInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}