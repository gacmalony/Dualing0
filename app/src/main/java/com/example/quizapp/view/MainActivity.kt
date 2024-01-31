package com.example.quizapp.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.model.ModelItem
import com.example.quizapp.viewmodel.MyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    //For change the data in layout
    lateinit var binding:ActivityMainBinding

    //Bringing data
    lateinit var quizViewModel:MyViewModel
    lateinit var questionsList:List<ModelItem>

    //Other counters
    companion object{
        var result = 0
        var total_question = 0
        var current_question = 0
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Alert Message
        showAlert(this, "ALERT!!!", "LOOK! DO YOU KNOW WHO IS HE? HE IS WILLIAM FRISH. He developed of the first written university examination. IF YOU WANNA GO NEXT QUESTIONS, YOU HAVE TO PUNCH HIS IMAGE");

        //Data Binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // RESET
        result = 0
        total_question = 0
        current_question = 0


        quizViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        //I have to work with main thread, because some other tasks using background thread(check repository).
        GlobalScope.launch(Dispatchers.Main) {
            //Bringing the first question
            quizViewModel.getQuestionsFromLiveData().observe(this@MainActivity, Observer {

                if (it.size > 0) {
                    questionsList = it

                    binding.apply {
                        question.text = questionsList!![0].question
                        option1.text = questionsList!![0].option1
                        option2.text = questionsList!![0].option2
                        option3.text = questionsList!![0].option3
                        option4.text = questionsList!![0].option4

                    }

                }
            })
        }


        var i = 1
        var chose = ""
        binding.apply {
            // This assigner is doing, when user click any "ANSWER CARD" changing the card colors and fetching the answer text that which one user clicked
            fun genius_assigner(view: TextView, card: CardView) {
                chose = view.text.toString()
                card1.setCardBackgroundColor(resources.getColor(R.color.blue))
                card2.setCardBackgroundColor(resources.getColor(R.color.blue))
                card3.setCardBackgroundColor(resources.getColor(R.color.blue))
                card4.setCardBackgroundColor(resources.getColor(R.color.blue))
                card.setCardBackgroundColor(R.drawable.youre_right)
            }

            //Listener, with when function calling genius_assigner function
            var cardClickListener = View.OnClickListener { view ->
                // Handle click based on the view's ID
                when (view.id) {
                    R.id.option1 -> genius_assigner(option1, card1)
                    R.id.option2 -> genius_assigner(option2, card2)
                    R.id.option3 -> genius_assigner(option3, card3)
                    R.id.option4 -> genius_assigner(option4, card4)
                    else -> Toast.makeText(
                        applicationContext,
                        "Click Explicitly!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            //Listening to click
            option1.setOnClickListener(cardClickListener)
            option2.setOnClickListener(cardClickListener)
            option3.setOnClickListener(cardClickListener)
            option4.setOnClickListener(cardClickListener)


            //If users sure about their answer, they have to click imagebutton which standing top of Interface.
            //Were listening the this imagebutton, when clicked we'll check the answer and do other stuff.
            imageView.setOnClickListener() {

                //Going through questionsList
                questionsList.let {
                    if (i < it.size!!) {
                        total_question = it.size
                        ++current_question

                        //Checking equality
                        if (chose.equals(it[i - 1].correct_option)) {
                            ++result
                            //If equal, changing the color of all cards and sending TOAST message.
                            card1.setCardBackgroundColor(R.drawable.youre_right)
                            card2.setCardBackgroundColor(R.drawable.youre_right)
                            card3.setCardBackgroundColor(R.drawable.youre_right)
                            card4.setCardBackgroundColor(R.drawable.youre_right)
                            Toast.makeText(
                                applicationContext,
                                "Correct Answer ${result}/$current_question",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            //If user answer and correct option dont equal, changing the color of cards to red
                            card1.setCardBackgroundColor(resources.getColor(R.color.red))
                            card2.setCardBackgroundColor(resources.getColor(R.color.red))
                            card3.setCardBackgroundColor(resources.getColor(R.color.red))
                            card4.setCardBackgroundColor(resources.getColor(R.color.red))
                            Toast.makeText(applicationContext, "False Answer,sorry buddy!",Toast.LENGTH_SHORT).show()
                            Toast.makeText(
                                applicationContext,
                                "Correct Answer ${result}/$current_question",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        //Bringing another question
                        question.text = "Question ${i + 1}: " + questionsList[i].question
                        option1.text = questionsList[i].option1
                        option2.text = questionsList[i].option2
                        option3.text = questionsList[i].option3
                        option4.text = questionsList[i].option4

                        //Delay
                        val delayMillis = 1000L // 5 seconds in milliseconds

                        // Main thread sleep using Handler
                        // Im using Back and Main thread together, thats the reason when using delay i have to be so careful.
                        // I dont make sleep to all main thread, just a few function.Thread.sleep() could kill the program
                        Handler(Looper.getMainLooper()).postDelayed({
                            card1.setCardBackgroundColor(resources.getColor(R.color.blue))
                            card2.setCardBackgroundColor(resources.getColor(R.color.blue))
                            card3.setCardBackgroundColor(resources.getColor(R.color.blue))
                            card4.setCardBackgroundColor(resources.getColor(R.color.blue))

                        }, delayMillis)





                        //CHECKING LAST QUESTION
                        if (i == it.size!!.minus(1)) {
                            Toast.makeText(
                                applicationContext,
                                "This is last question",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        i++


                    } else {
                        // NOW WE'RE CHECKING EQUALITY OF LAST QUESTION AND SETTING THE COLOR IF ITS CORRECT
                        if (chose.equals(it[i - 1].correct_option)) {
                            card1.setCardBackgroundColor(R.drawable.youre_right)
                            card2.setCardBackgroundColor(R.drawable.youre_right)
                            card3.setCardBackgroundColor(R.drawable.youre_right)
                            card4.setCardBackgroundColor(R.drawable.youre_right)
                            result++
                            Toast.makeText(
                                applicationContext,
                                "Correct Answer ${result}/$current_question",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            // FOR LAST QUESTION IF ITS NOT CORRECT
                            card1.setCardBackgroundColor(resources.getColor(R.color.red))
                            card2.setCardBackgroundColor(resources.getColor(R.color.red))
                            card3.setCardBackgroundColor(resources.getColor(R.color.red))
                            card4.setCardBackgroundColor(resources.getColor(R.color.red))

                        }

                        //LAST DELAY BEFORE SEND TO ANOTHER ACTIVITY,I ADDED TO DISPLAY THE "CARD COLOR CHANGING FEATURE" EXPLICITLY                        val delayMillis = 2000L
                        val delayMillis = 2000L
                        // Main thread sleep using Handler
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this@MainActivity, ResultActivity::class.java)
                            startActivity(intent)
                            finish()

                        }, delayMillis)

                    }
                }
            }
        }

        }

    //ALERT MESSAGE
    fun showAlert(context: Context?, title: String?, message: String?) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(context, "PUNCH HIM!", Toast.LENGTH_SHORT).show()
            })
            .show()
    }

    }


