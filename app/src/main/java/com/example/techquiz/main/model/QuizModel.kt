package com.example.techquiz.main.model

data class QuizModel(
    val id: String?,
    val title: String?,
    val subTitle: String?,
    val time: Int?,
    val question: List<QuestionModel>
){
    constructor() : this("","","",1, emptyList())
}


data class QuestionModel(
    val question: String,
    val options: List<String>,
    val correct: String
){
    constructor() : this ("", emptyList(),"")
}
