package com.example.techquiz.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techquiz.R
import com.example.techquiz.databinding.FragmentLandingBinding
import com.example.techquiz.databinding.ScoreDialogBinding
import com.example.techquiz.main.LandingViewModel
import com.example.techquiz.main.adapter.QuizCategoryAdapter

class LandingFragment : Fragment() {

    lateinit var binding: FragmentLandingBinding
    private lateinit var viewModel : LandingViewModel
    lateinit var adapter: QuizCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Getting a ViewModel instance
        viewModel = ViewModelProvider(requireActivity())[LandingViewModel::class.java]
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
        {
            val bundle = Bundle()
            bundle.putString("quiz_model_id",it.id)
            findNavController().navigate(R.id.action_landingFragment_to_quizFragment,bundle)
        }
        binding.categoryRv.layoutManager = LinearLayoutManager(context)
        binding.categoryRv.adapter = adapter
    }
}