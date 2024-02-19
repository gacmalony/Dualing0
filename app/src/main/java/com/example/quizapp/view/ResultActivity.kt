package com.example.quizapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    lateinit var binding:ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_result)

        binding.result.text = "Your Result ${MainActivity.result}/${MainActivity.total_question}"
        checkGreaterThan(MainActivity.result)


        binding.werebackbaby.setOnClickListener(){
            val i = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(i)
        }

    }
        //MATCHING RESULT WITH SOME TEXTS
        fun checkGreaterThan(value: Int){
            when {
                value < (MainActivity.total_question / 4) -> binding.meaning.text = "You have absolutely bad memory"
                value <= (MainActivity.total_question / 2) -> binding.meaning.text = "Your iq is average"
                value > (MainActivity.total_question / 2) && value < ((MainActivity.total_question / 4)*3) -> binding.meaning.text = "You did more than half, its cool!"
                value > ((MainActivity.total_question / 4)*3) -> binding.meaning.text = "Youre smart man!"
                else->
                    binding.meaning.text = "I couldnt define you, i dont know why?"


            }
        }
}