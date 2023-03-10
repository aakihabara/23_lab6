package com.example.secondapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import java.security.KeyStore.TrustedCertificateEntry

private const val TAG = "MainActivity"

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

    private val isAnswered = IntArray(questionBank.size)

    private val correctAnswer = BooleanArray(questionBank.size)

    private var currentIndex = 0

    private fun toastMessage(text: String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun checkOnAvaliableButtons(){
        if(checkOnAnswered()){
            true_button.isEnabled = false
            false_button.isEnabled = false
        }
        else {
            true_button.isEnabled = true
            false_button.isEnabled = true
        }
    }

    private fun checkOnAnswered() : Boolean{
        return isAnswered[currentIndex] == 1
    }

    private fun checkIfFinish(){
        var finishing = true
        for(i in isAnswered) {
            if (i == 0) {
                finishing = false
            }
        }

        if(finishing){
            var count = 0

            for(i in correctAnswer){
                if(i){
                    count++
                }
            }

            var result = count * 100 / questionBank.size

            Toast.makeText(this, "$result% correct answers", Toast.LENGTH_SHORT).show()
        }

    }

    private fun buildTextView(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun incIndex(){
        if(currentIndex == questionBank.size - 1)
        {
            //do nothing
        }
        else {
            currentIndex = (currentIndex + 1) % questionBank.size
        }

        checkOnAvaliableButtons()

        buildTextView()
    }

    private fun decIndex(){
        if (currentIndex == 0){
            //do nothing
        }
        else {
            currentIndex = (currentIndex - 1) % questionBank.size
        }

        checkOnAvaliableButtons()

        buildTextView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        true_button = findViewById(R.id.true_button)
        false_button = findViewById(R.id.false_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        true_button.setOnClickListener{

            if(questionBank[currentIndex].answer){
                toastMessage("Correct")
                correctAnswer[currentIndex] = true
            }
            else {
                toastMessage("Incorrect")
                correctAnswer[currentIndex] = false
            }

            isAnswered[currentIndex] = 1

            checkOnAvaliableButtons()

            checkIfFinish()

        }

        false_button.setOnClickListener{

            if(!questionBank[currentIndex].answer){
                toastMessage("Correct")
                correctAnswer[currentIndex] = true
            }
            else {
                toastMessage("Incorrect")
                correctAnswer[currentIndex] = false
            }



            isAnswered[currentIndex] = 1

            checkOnAvaliableButtons()

            checkIfFinish()

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

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart(Bundle?) called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume(Bundle?) called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause(Bundle?) called")
    }

    override fun onStop(){
        super.onStop()
        Log.d(TAG, "onStop(Bundle?) called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy(Bundle?) called")
    }
}