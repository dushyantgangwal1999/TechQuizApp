package com.example.techquiz.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.techquiz.main.model.QuestionModel
import com.example.techquiz.main.model.QuizModel
import com.google.firebase.database.FirebaseDatabase

/**
 * ViewModel
 */
class LandingViewModel : ViewModel() {

    private val quizModelList = mutableListOf<QuizModel>()

    private val _list = MutableLiveData<List<QuizModel>>()
    val list: LiveData<List<QuizModel>> get() = _list

    fun getDataFromFirebase(){
        quizModelList.clear()
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener {dataSnapShot ->
                if (dataSnapShot.exists()){
                    for (snapShot in dataSnapShot.children){
                        val quizModel = snapShot.getValue(QuizModel::class.java)
                        if (quizModel != null){
                            quizModelList.add(quizModel)
                        }
                    }
                }

            }
            .addOnCompleteListener {
                Log.d("LandingVM","Complete Listener")
                _list.value = quizModelList
            }
    }
}