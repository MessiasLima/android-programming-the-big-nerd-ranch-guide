package com.bignerdranch.android.geoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_1, false),
        Question(R.string.question_2, true),
        Question(R.string.question_3, false),
        Question(R.string.question_4, true),
        Question(R.string.question_5, false),
    )

    var currentIndex = 0

    val currentQuestion: Question
        get() = questionBank[currentIndex]

    fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun previousQuestion() {
        currentIndex = (currentIndex - 1) % questionBank.size
    }

    fun isNextButtonEnabled(): Boolean {
        return currentIndex < (questionBank.size - 1)
    }

    fun isPreviousButtonEnabled(): Boolean {
        return currentIndex >= 1
    }

    fun isEligibleToShowResult(): Boolean {
        return questionBank.all { it.wasAnswered }
    }

    fun getAccuracyPercentage(): Float {
        val ratio = questionBank.count { it.answeredCorrectly } / questionBank.size.toFloat()
        return ratio * 100
    }
}