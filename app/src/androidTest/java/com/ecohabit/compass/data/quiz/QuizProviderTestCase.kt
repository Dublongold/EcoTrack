package com.ecohabit.ecotrack.data.quiz

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Test

class QuizProviderTestCase {
    lateinit var quizProvider: QuizProvider
    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        quizProvider = QuizProvider(context)
    }

    @After
    fun tearDown() {

    }

    @Test fun quizItemsCreatedSuccessfully() {
        val questions = quizProvider.food
        assert(questions.isNotEmpty()) { "Quiz questions should not be empty." }
    }
}