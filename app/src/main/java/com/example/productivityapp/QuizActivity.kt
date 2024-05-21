package com.example.productivityapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.productivityapp.databinding.ActivityQuizBinding
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
   private lateinit var response: GenerateContentResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonProductStart.setOnClickListener{


            val generativeModel = GenerativeModel(
                modelName = "gemini-1.5-pro-latest",
                apiKey = Constants.API_KEY
            )
            val prompt = "Please write me a paragraph about why my friend evan is dumb"
            MainScope().launch {
                 response = generativeModel.generateContent(prompt)
                binding.textViewProductResponse.text = response.text
            }
        }
    }
}