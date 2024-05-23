package com.example.productivityapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.example.productivityapp.databinding.ActivityFlashcardBinding
import com.example.productivityapp.databinding.ActivityQuizBinding
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class Flashcard : AppCompatActivity() {
    private lateinit var binding: ActivityFlashcardBinding
    private lateinit var response: GenerateContentResponse
    private lateinit var list: String
    private lateinit var userResponse: String
    private lateinit var quizValue: List<String>
    private var quizIteration: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashcardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonFlashcardFlashcard.isVisible = false
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-pro-latest",
            apiKey = Constants.API_KEY
        )
        binding.buttonFlashcardSubmit.setOnClickListener{
            userResponse = binding.editTextFlashcardUserResponse.text.toString()
            binding.buttonFlashcardSubmit.isVisible = false
            binding.editTextFlashcardUserResponse.isVisible = false
            quizValue = userResponse.split("/")
            println(quizValue)
            binding.buttonFlashcardFlashcard.isVisible = true
        }


        val prompt = ""
        MainScope().launch {
            response = generativeModel.generateContent(prompt)
            list = response.text.toString()

        }
            binding.buttonFlashcardFlashcard.setOnClickListener {
                if (quizIteration % 2 == 0)
                {
                    quizIteration ++
                    binding.buttonFlashcardFlashcard.text = list[quizIteration].toString()
                }
                else{
                    quizIteration --
                    binding.buttonFlashcardFlashcard.text = list[quizIteration].toString()
                }


            }


    }
}