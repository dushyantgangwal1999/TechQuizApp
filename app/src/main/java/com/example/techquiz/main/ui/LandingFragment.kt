package com.example.techquiz.main.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techquiz.R
import com.example.techquiz.databinding.FragmentLandingBinding
import com.example.techquiz.main.LandingViewModel
import com.example.techquiz.main.adapter.QuizCategoryAdapter

class LandingFragment : Fragment() {

    lateinit var binding: FragmentLandingBinding
    private lateinit var viewModel : LandingViewModel
    lateinit var adapter: QuizCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Getting a ViewModel instance
        viewModel = ViewModelProvider(this)[LandingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLandingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCategory()
    }

    private fun setUpCategory(){
        val data = viewModel.getDummyData()
        adapter = QuizCategoryAdapter(data)
        binding.categoryRv.layoutManager = LinearLayoutManager(context)
        binding.categoryRv.adapter = adapter
    }
}