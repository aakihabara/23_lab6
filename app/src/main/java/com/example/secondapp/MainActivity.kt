package com.example.secondapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast



class MainActivity : AppCompatActivity() {

    private lateinit var true_button: Button
    private lateinit var false_button:Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(Questions(R.string.question_australia, true),
        Questions(R.string.question_oceans, true),
        Questions(R.string.question_mideast, false),
        Questions(R.string.question_africa, false),
        Questions(R.string.question_asia, true),
        Questions(R.string.question_america, true)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun toastMessage(text: String){
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
        }

        fun buildTextView(){
            val questionTextResId = questionBank[currentIndex].textResId
            questionTextView.setText(questionTextResId)
        }

        fun incIndex(){
            if(currentIndex == questionBank.size - 1)
            {
                //do nothing
            }
            else {
                currentIndex = (currentIndex + 1) % questionBank.size
            }
            buildTextView()
        }

        fun decIndex(){
            if (currentIndex == 0){
                //do nothing
            }
            else {
                currentIndex = (currentIndex - 1) % questionBank.size
            }
            buildTextView()
        }

        true_button = findViewById(R.id.true_button)
        false_button = findViewById(R.id.false_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        true_button.setOnClickListener{

            if(questionBank[currentIndex].answer){
                toastMessage("Correct")
            }
            else {
                toastMessage("Incorrect")
            }

            incIndex()

        }

        false_button.setOnClickListener{

            if(!questionBank[currentIndex].answer){
                toastMessage("Correct")
            }
            else {
                toastMessage("Incorrect")
            }

            incIndex()

        }

        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)

        questionTextView.setOnClickListener{
            incIndex()
        }

        nextButton.setOnClickListener{
            incIndex()
        }

        prevButton.setOnClickListener{
            decIndex()
        }
    }
}