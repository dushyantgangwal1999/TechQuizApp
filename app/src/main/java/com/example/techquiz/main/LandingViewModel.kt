package com.example.techquiz.main

import androidx.lifecycle.ViewModel
import com.example.techquiz.main.model.QuizModel

class LandingViewModel : ViewModel() {


    fun getDummyData(): MutableList<QuizModel>{
        val quizModel = mutableListOf<QuizModel>()
        quizModel.add(QuizModel("1","Web Dev","Quiz for Web Developers","10"))
        quizModel.add(QuizModel("2","Ios Dev","Quiz for Ios Developers","10"))
        quizModel.add(QuizModel("3","Android Dev","Quiz for Android Developers","10"))
        return quizModel
    }
}