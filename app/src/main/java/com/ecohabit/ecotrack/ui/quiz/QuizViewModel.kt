package com.ecohabit.ecotrack.ui.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ecohabit.ecotrack.data.quiz.QuizProvider
import com.ecohabit.ecotrack.data.quiz.QuizQuestion

class QuizViewModel(
    quizProvider: QuizProvider
) : ViewModel() {
    val quizItems = quizProvider.food


    var stage by mutableStateOf(QuizStage.Start)
        private set
    var currentQuestionIndex by mutableIntStateOf(0)
        private set
    val currentQuestion: QuizQuestion
        get() = quizItems[currentQuestionIndex]
    var selectedOptionIndex by mutableStateOf<Int?>(null)
        private set
    var score by mutableIntStateOf(0)
        private set

    fun updateSelectedOptionIndex(value: Int) {
        if (value in currentQuestion.options.indices) {
            selectedOptionIndex = value
        } else {
            throw IllegalArgumentException("Selected option index is out of bounds.")
        }
    }

    fun goToStartStage() {
        stage = QuizStage.Start
    }

    fun startQuiz() {
        currentQuestionIndex = 0
        selectedOptionIndex = null
        score = 0
        stage = QuizStage.Quiz
    }

    fun submitAnswer() {
        val selected = selectedOptionIndex ?: return
        if (selected == quizItems[currentQuestionIndex].correctOptionIndex) {
            score++
        }
        if (currentQuestionIndex < quizItems.lastIndex) {
            currentQuestionIndex++
            selectedOptionIndex = null
        } else {
            stage = QuizStage.Result
        }
    }
}