package com.ecohabit.ecotrack.data.quiz

import android.content.Context
import org.json.JSONArray

private const val JSON_FILENAME = "quiz.json"

class QuizProvider(context: Context) {
    val food = readFromAssets(context)
    fun readFromAssets(
        context: Context,
    ): List<QuizQuestion> {
        val json = context.assets.open(JSON_FILENAME)
            .bufferedReader()
            .use { it.readText() }

        val array = JSONArray(json)
        val questions = mutableListOf<QuizQuestion>()

        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)

            val questionTitle = item.getString("question")
            val answersJson = item.getJSONArray("answers")

            val options = mutableListOf<String>()
            var correctIndex = -1

            for (j in 0 until answersJson.length()) {
                val rawAnswer = answersJson.getString(j)

                if (rawAnswer.startsWith("+")) {
                    correctIndex = j
                    options.add(rawAnswer.removePrefix("+").trim())
                } else {
                    options.add(rawAnswer.trim())
                }
            }

            require(correctIndex != -1) {
                "Question at index $i has no correct answer marked with '+'."
            }

            questions.add(
                QuizQuestion(
                    title = questionTitle,
                    options = options,
                    correctOptionIndex = correctIndex
                )
            )
        }

        return questions
    }
}