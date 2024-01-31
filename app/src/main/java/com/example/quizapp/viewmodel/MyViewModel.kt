package com.example.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.model.MyModel
import com.example.quizapp.repository.MyRepository

class MyViewModel:ViewModel() {

    var myRepository:MyRepository = MyRepository()


    lateinit var questionsLiveData:LiveData<MyModel>

    init {
        questionsLiveData = myRepository.getQuestionsFromAPI()
    }

    fun getQuestionsFromLiveData():LiveData<MyModel>{
        return questionsLiveData
    }
}