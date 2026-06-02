package com.ecohabit.ecotrack.data.quiz

data class QuizQuestion(
    val title: String,
    val options: List<String>,
    val correctOptionIndex: Int
)