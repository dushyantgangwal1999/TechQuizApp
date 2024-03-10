package com.example.techquiz.main.ui

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.techquiz.R
import com.example.techquiz.databinding.FragmentQuizBinding
import com.example.techquiz.databinding.ScoreDialogBinding
import com.example.techquiz.main.LandingViewModel
import com.example.techquiz.main.model.QuestionModel
import kotlin.text.StringBuilder

class QuizFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentQuizBinding
    private var questions: List<QuestionModel>? = null
    private var timer: Int? = 1
    private var quizID: String? = null
    private lateinit var viewModel: LandingViewModel
    private var currentQuestion = 0
    private var selectedAnswer: String? = ""
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[LandingViewModel::class.java]
        quizID = arguments?.getString("quiz_model_id", "")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpClickListener()
        setUpObserver()
    }

    private fun setUpClickListener() {
        binding.apply {
            this.btn0.setOnClickListener(this@QuizFragment)
            this.btn1.setOnClickListener(this@QuizFragment)
            this.btn2.setOnClickListener(this@QuizFragment)
            this.btn3.setOnClickListener(this@QuizFragment)
            this.nextBtn.setOnClickListener(this@QuizFragment)
        }
    }

    /**
     * Fun to setUp observer
     */
    private fun setUpObserver() {
        Log.d("QuizFragment", "setUpObserver Called")
        viewModel.list.observe(viewLifecycleOwner, Observer { it ->
            // Use the received list here
            questions = it.find { quizModel ->
                quizModel.id == quizID
            }?.question

            timer = it.find { quizModel ->
                quizModel.id == quizID
            }?.time

            questions?.let {
                loadQuestion()
                startTimer()
            }
        })
    }

    /**
     * Fun to start Timer
     */
    private fun startTimer() {
        Log.d("QuizFragment", "Next Button Clicked")
        val totalTimeInMillis = ((timer?.toInt()?.times(60) ?: 1) * 1000).toLong()
        object : CountDownTimer(totalTimeInMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val minutes = seconds / 60
                val remainingSeconds = seconds % 60
                binding.timerIndicatorTextview.text =
                    String.format("%02d:%02d", minutes, remainingSeconds)
            }

            override fun onFinish() {
                // Finish the Quiz
            }

        }.start()
    }

    /**
     * Fun to load Timer
     */
    private fun loadQuestion() {
        selectedAnswer = ""
        if (currentQuestion == questions?.size) {
            quitQuiz()
            return
        }
        Log.d("QuizFragment", "loadQuestion Called ${questions}")
        val quesIndicator = StringBuilder()
        quesIndicator.append("Questions")
            .append(currentQuestion + 1)
            .append("/")
            .append(questions?.size)
        binding.questionIndicatorTextview.text = quesIndicator
        binding.questionProgressIndicator.progress =
            ((currentQuestion + 1).toFloat() / (questions?.size ?: 0).toFloat() * 100).toInt()
        if (currentQuestion < (questions?.size ?: 0)) {
            questions?.let {
                val currentQuestion = it[currentQuestion]
                binding.questionTextview.text = currentQuestion.question
                binding.btn0.text = currentQuestion.options[0]
                binding.btn1.text = currentQuestion.options[1]
                binding.btn2.text = currentQuestion.options[2]
                binding.btn3.text = currentQuestion.options[3]
            }
        }
    }

    override fun onDestroy() {
        Log.d("QuizFragment", "onDestroy Called")
        currentQuestion = 0
        super.onDestroy()
    }

    private fun setButtonToDefault() {
        binding.apply {
            binding.btn0.setBackgroundColor(resources.getColor(R.color.gray))
            binding.btn1.setBackgroundColor(resources.getColor(R.color.gray))
            binding.btn2.setBackgroundColor(resources.getColor(R.color.gray))
            binding.btn3.setBackgroundColor(resources.getColor(R.color.gray))
        }
    }

    /**
     * Fun to update after nxt button Click
     */
    private fun updateScore() {
        if (!selectedAnswer.isNullOrEmpty()) {
            val correctAnswer = questions?.let {
                it[currentQuestion].correct
            }
            if (correctAnswer?.equals(selectedAnswer, true) == true) {
                score++
            }
            Log.d("QuizFragment", "upDate to Score $score $correctAnswer  $selectedAnswer")
        }
    }

    override fun onClick(v: View?) {
        Log.d("QuizFragment", "onClick Called")
        setButtonToDefault()
        val clickedButton = v as? Button
        when (clickedButton?.id) {
            R.id.next_btn -> {
                updateScore()
                currentQuestion++
                loadQuestion()
            }

            R.id.btn0 -> {
                binding.btn0.setBackgroundColor(resources.getColor(R.color.orange))
                selectedAnswer = questions?.let {
                    it[currentQuestion].options[0]
                }
            }

            R.id.btn1 -> {
                binding.btn1.setBackgroundColor(resources.getColor(R.color.orange))
                selectedAnswer = questions?.let {
                    it[currentQuestion].options[1]
                }
            }

            R.id.btn2 -> {
                binding.btn2.setBackgroundColor(resources.getColor(R.color.orange))
                selectedAnswer = questions?.let {
                    it[currentQuestion].options[2]
                }
            }

            R.id.btn3 -> {
                binding.btn3.setBackgroundColor(resources.getColor(R.color.orange))
                selectedAnswer = questions?.let {
                    it[currentQuestion].options[3]
                }
            }
        }

    }

    private fun quitQuiz() {
        val totalQuestions = questions?.size ?: 1
        val percentage = ((score.toFloat() / totalQuestions.toFloat()) * 100).toInt()

        val dialogBinding = ScoreDialogBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(false)
            .show()
        dialogBinding.apply {
            scoreProgressIndicator.progress = percentage
            scoreProgressText.text = "$percentage %"
            if (percentage > 60) {
                scoreTitle.text = "Congrats! You have passed"
                scoreTitle.setTextColor(Color.BLUE)
            } else {
                scoreTitle.text = "Oops! You have failed"
                scoreTitle.setTextColor(Color.RED)
            }
            scoreSubtitle.text = "$score out of $totalQuestions are correct"
            finishBtn.setOnClickListener {
                findNavController().popBackStack()
                dialog.dismiss()
            }
        }
    }
}