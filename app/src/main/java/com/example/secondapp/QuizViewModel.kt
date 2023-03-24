package com.example.secondapp

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel(){

    private val questionBank = listOf(Questions(R.string.question_australia, true),
        Questions(R.string.question_oceans, true),
        Questions(R.string.question_mideast, false),
        Questions(R.string.question_africa, false),
        Questions(R.string.question_asia, true),
        Questions(R.string.question_america, true)
    )

    var currentIndex = 0

    val isAnswered = IntArray(questionBank.size)

    val isCheated = BooleanArray(questionBank.size)

    val correctAnswer = BooleanArray(questionBank.size)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val currentQuestionSize: Int
        get() = questionBank.size

    fun moveToNext() {
        if(currentIndex == questionBank.size - 1)
        {
            //do nothing
        }
        else {
            currentIndex = (currentIndex + 1) % questionBank.size
        }
    }

    fun moveToPrev() {
        if (currentIndex == 0){
            //do nothing
        }
        else {
            currentIndex = (currentIndex - 1) % questionBank.size
        }
    }

}