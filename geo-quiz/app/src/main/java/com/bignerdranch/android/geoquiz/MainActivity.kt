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
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val viewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)
        viewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
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
        viewModel.nextQuestion()
        updateQuestion()
    }

    private fun previousQuestion() {
        viewModel.previousQuestion()
        updateQuestion()
    }

    private fun updateQuestion() {
        trueButton.isEnabled = !viewModel.currentQuestion.wasAnswered
        falseButton.isEnabled = !viewModel.currentQuestion.wasAnswered
        nextButton.isEnabled = viewModel.isNextButtonEnabled()
        prevButton.isEnabled = viewModel.isPreviousButtonEnabled()
        questionTextView.setText(viewModel.currentQuestion.textResId)
    }

    private fun submitAnswer(answer: Boolean) {
        viewModel.currentQuestion.wasAnswered = true
        val message = if (answer == viewModel.currentQuestion.answer) {
            viewModel.currentQuestion.answeredCorrectly = true
            R.string.toast_correct
        } else {
            R.string.toast_incorrect
        }

        showToast(message)
        updateQuestion()
        checkShowResultEligibility()
    }

    private fun checkShowResultEligibility() {
        if (viewModel.isEligibleToShowResult()) {
            showResult()
        }
    }

    private fun showResult() {
        showToast(getString(R.string.result, viewModel.getAccuracyPercentage()))
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, viewModel.currentIndex)
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val KEY_INDEX = "index"
    }
}