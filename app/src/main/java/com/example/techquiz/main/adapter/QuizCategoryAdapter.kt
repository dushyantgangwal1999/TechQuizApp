package com.example.techquiz.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.techquiz.databinding.QuizCategoryItemRowBinding
import com.example.techquiz.main.model.QuizModel

class QuizCategoryAdapter(private val quizCategoryList : List<QuizModel>):
    RecyclerView.Adapter<QuizCategoryAdapter.MyViewHolder>() {


    class MyViewHolder(private val binding: QuizCategoryItemRowBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(model: QuizModel){
            binding?.apply {
                quizTitleText.text = model.title ?: "NA"
                quizSubtitleText.text = model.subTitle
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = QuizCategoryItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return quizCategoryList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(quizCategoryList[position])
    }
}