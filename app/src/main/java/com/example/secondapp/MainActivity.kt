package com.example.secondapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.security.KeyStore.TrustedCertificateEntry

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var true_button: Button
    private lateinit var false_button:Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    private fun toastMessage(text: String){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun checkIfFinish(){
        var finishing = true
        for(i in quizViewModel.isAnswered) {
            if (i == 0) {
                finishing = false
            }
        }

        if(finishing){
            var count = 0

            for(i in quizViewModel.correctAnswer){
                if(i){
                    count++
                }
            }

            var result = count * 100 / quizViewModel.currentQuestionSize

            Toast.makeText(this, "$result% correct answers", Toast.LENGTH_SHORT).show()
        }

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

    private fun buildTextView(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkOnAnswered() : Boolean{
        return quizViewModel.isAnswered[quizViewModel.currentIndex] == 1
    }

    private fun checkOnNPButtons(){
        when (quizViewModel.currentIndex) {
            0 -> {
                nextButton.isEnabled = true
                questionTextView.isClickable = true
                prevButton.isEnabled = false
            }
            (quizViewModel.currentQuestionSize - 1) -> {
                nextButton.isEnabled = false
                questionTextView.isClickable = false
                prevButton.isEnabled = true
            }
            else -> {
                nextButton.isEnabled = true
                questionTextView.isClickable = true
                prevButton.isEnabled = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        true_button = findViewById(R.id.true_button)
        false_button = findViewById(R.id.false_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        checkOnAvaliableButtons()
        checkOnNPButtons()

        true_button.setOnClickListener{

            if(quizViewModel.currentQuestionAnswer){
                toastMessage("Correct")
                quizViewModel.correctAnswer[quizViewModel.currentIndex] = true
            }
            else {
                toastMessage("Incorrect")
                quizViewModel.correctAnswer[quizViewModel.currentIndex] = false
            }

            quizViewModel.isAnswered[quizViewModel.currentIndex] = 1

            checkOnAvaliableButtons()

            checkIfFinish()
        }

        false_button.setOnClickListener{

            if(!quizViewModel.currentQuestionAnswer){
                toastMessage("Correct")
                quizViewModel.correctAnswer[quizViewModel.currentIndex] = true
            }
            else {
                toastMessage("Incorrect")
                quizViewModel.correctAnswer[quizViewModel.currentIndex] = false
            }

            quizViewModel.isAnswered[quizViewModel.currentIndex] = 1

            checkOnAvaliableButtons()

            checkIfFinish()

        }

        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)

        questionTextView.setOnClickListener{
            quizViewModel.moveToNext()
            checkOnAvaliableButtons()
            buildTextView()
            checkOnNPButtons()
        }

        nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            checkOnAvaliableButtons()
            buildTextView()
            checkOnNPButtons()
        }

        prevButton.setOnClickListener{
            quizViewModel.moveToPrev()
            checkOnAvaliableButtons()
            buildTextView()
            checkOnNPButtons()
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

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
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