package com.example.secondapp

import android.app.Activity
import android.content.Intent
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
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var true_button: Button
    private lateinit var false_button:Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button

    private var userAnswer = false;

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

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer: Boolean = quizViewModel.currentQuestionAnswer

        val messageResId = when {
            quizViewModel.isCheated[quizViewModel.currentIndex] -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
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
        cheatButton = findViewById(R.id.cheat_button)

        checkOnAvaliableButtons()
        checkOnNPButtons()

        true_button.setOnClickListener{

            checkAnswer(true)

            quizViewModel.correctAnswer[quizViewModel.currentIndex] = quizViewModel.currentQuestionAnswer

            quizViewModel.isAnswered[quizViewModel.currentIndex] = 1

            checkOnAvaliableButtons()

            checkIfFinish()
        }

        false_button.setOnClickListener{

            checkAnswer(false)

            quizViewModel.correctAnswer[quizViewModel.currentIndex] = !quizViewModel.currentQuestionAnswer

            quizViewModel.isAnswered[quizViewModel.currentIndex] = 1

            checkOnAvaliableButtons()

            checkIfFinish()

        }

        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
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

    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK){
            return
        }

        if(requestCode == REQUEST_CODE_CHEAT){
            quizViewModel.isCheated[quizViewModel.currentIndex] =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }
}