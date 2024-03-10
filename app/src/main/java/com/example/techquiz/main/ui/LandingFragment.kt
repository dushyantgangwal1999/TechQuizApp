package com.example.techquiz.main.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techquiz.R
import com.example.techquiz.databinding.FragmentLandingBinding
import com.example.techquiz.databinding.ScoreDialogBinding
import com.example.techquiz.main.LandingViewModel
import com.example.techquiz.main.adapter.QuizCategoryAdapter
import com.example.techquiz.main.model.QuizModel

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
        viewModel.getDataFromFirebase()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
    }

    private fun setUpObserver(){
        viewModel.list.observe(viewLifecycleOwner, Observer {
            setUpCategory(it)
        })
    }
    private fun setUpCategory(quizCategoryList: List<QuizModel>){
        Log.d("LandingFragment","setUpCategory Called $quizCategoryList")
        adapter = QuizCategoryAdapter(quizCategoryList)
        {
            val bundle = Bundle()
            bundle.putString("quiz_model_id",it.id)
            findNavController().navigate(R.id.action_landingFragment_to_quizFragment,bundle)

        }
        binding.categoryRv.layoutManager = LinearLayoutManager(context)
        binding.categoryRv.adapter = adapter
    }
}