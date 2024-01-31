package com.example.quizapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizapp.api.QuestionsAPI
import com.example.quizapp.api.QuestionsFetcher
import com.example.quizapp.model.MyModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyRepository {

    var questionsAPI: QuestionsAPI

    init{
        questionsAPI = QuestionsFetcher().getRetrofitInstance()
            .create(QuestionsAPI::class.java)
    }


    fun getQuestionsFromAPI():LiveData<MyModel> {
        var data = MutableLiveData<MyModel>()

        var questionsList: MyModel

        GlobalScope.launch(Dispatchers.IO) {
            val response = questionsAPI.getQuestions()

            if (response.body() != null) {

                questionsList = response.body()!!
                data.postValue(questionsList)
                Log.i("TAGK", data.value.toString())
            }
            else{
                Log.i("TAGK", "Problem is you know")
            }
        }

        return data
    }
}