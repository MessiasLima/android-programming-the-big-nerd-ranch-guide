package com.bignerdranch.android.geoquiz

import androidx.annotation.StringRes

data class Question(
    @StringRes val textResId: Int,
    val answer: Boolean,
    var wasAnswered: Boolean = false,
    var answeredCorrectly: Boolean = false,
)
