package com.example.techquiz.main.model

data class QuizModel(
    val id: String?,
    val title: String?,
    val subTitle: String?,
    val time: String?,
    val question: List<QuestionModel>
)


data class QuestionModel(
    val question: String,
    val options: List<String>,
    val correct: String
)
