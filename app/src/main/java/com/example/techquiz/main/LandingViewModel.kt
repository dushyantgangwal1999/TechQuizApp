package com.example.techquiz.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techquiz.main.model.QuestionModel
import com.example.techquiz.main.model.QuizModel

class LandingViewModel : ViewModel() {


    private val _list = MutableLiveData<List<QuizModel>>()
    val list: LiveData<List<QuizModel>> get() = _list

    fun getDummyData(): MutableList<QuizModel> {
        val quizModel = mutableListOf<QuizModel>()
        val questionList = mutableListOf<QuestionModel>()
        questionList.add(
            QuestionModel(
                "What is Android?",
                listOf("Language", "OS", "IDE", "Application"),
                "OS"
            )
        )
        questionList.add(
            QuestionModel(
                "What is Java?",
                listOf( "OS","Language", "IDE", "Application"),
                "Language"
            )
        )
        questionList.add(
            QuestionModel(
                "Instagram is Owned by?",
                listOf( "Samsung","Android", "IDE", "Apple"),
                "Android"
            )
        )
        quizModel.add(QuizModel("1","Web Dev","Quiz for Web Developers","10",questionList))
//        quizModel.add(QuizModel("2","Ios Dev","Quiz for Ios Developers","10",questionList))
//        quizModel.add(QuizModel("3","Android Dev","Quiz for Android Developers","10",questionList))
        _list.value = quizModel
        return quizModel
    }
}