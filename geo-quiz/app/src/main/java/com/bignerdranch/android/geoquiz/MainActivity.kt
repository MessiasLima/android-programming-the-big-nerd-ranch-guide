package com.bignerdranch.android.geoquiz

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    private fun nextQuestion() {
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun previousQuestion() {
        currentIndex = (currentIndex - 1) % questionBank.size
        updateQuestion()
    }

    private fun updateQuestion() {
        questionTextView.setText(currentQuestion.textResId)
    }

    private fun submitAnswer(answer: Boolean) {
        val message = if (answer == currentQuestion.answer) {
            R.string.toast_correct
        } else {
            R.string.toast_incorrect
        }

        showToast(message)
    }

    private fun showToast(@StringRes message: Int) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 200)
        toast.show()
    }
}