package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_1, false),
        Question(R.string.question_2, true),
        Question(R.string.question_3, false),
        Question(R.string.question_4, true),
        Question(R.string.question_5, false),
    )

    private var currentIndex = 0
    private val currentQuestion: Question
        get() = questionBank[currentIndex]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            submitAnswer(true)
        }

        falseButton.setOnClickListener {
            submitAnswer(false)
        }

        nextButton.setOnClickListener {
            nextQuestion()
        }

        prevButton.setOnClickListener {
            previousQuestion()
        }

        questionTextView.setOnClickListener {
            nextQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    private fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun previousQuestion() {
        currentIndex = (currentIndex - 1) % questionBank.size
        updateQuestion()
    }

    private fun updateQuestion() {
        trueButton.isEnabled = !currentQuestion.wasAnswered
        falseButton.isEnabled = !currentQuestion.wasAnswered
        nextButton.isEnabled = currentIndex < (questionBank.size - 1)
        prevButton.isEnabled = currentIndex >= 1
        questionTextView.setText(currentQuestion.textResId)
    }

    private fun submitAnswer(answer: Boolean) {
        currentQuestion.wasAnswered = true
        val message = if (answer == currentQuestion.answer) {
            currentQuestion.answeredCorrectly = true
            R.string.toast_correct
        } else {
            R.string.toast_incorrect
        }

        showToast(message)
        updateQuestion()
        checkShowResultEligibility()
    }

    private fun checkShowResultEligibility() {
        if (questionBank.all { it.wasAnswered }) {
            showResult()
        }
    }

    private fun showResult() {
        val ratio = questionBank.count { it.answeredCorrectly } / questionBank.size.toFloat()
        val percentage = ratio * 100
        showToast(getString(R.string.result, percentage))
    }

    private fun showToast(@StringRes message: Int) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 200)
        toast.show()
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 200)
        toast.show()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}