package com.example.productivityapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private var quizValue: List<String> = listOf("Failure")
    private var quizIteration: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashcardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //making flashcard invisible
        binding.buttonFlashcardFlashcard.isVisible = false
        binding.imageButtonFlashcardLeft.isVisible = false
        binding.imageButtonFlashcardRight.isVisible = false
        binding.textViewFlashcardDirection.isVisible = false
        binding.textViewFlashcardNumber.isVisible = false
        var flashcardNumber: Int = 1
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-pro-latest",
            apiKey = Constants.API_KEY
        )

        binding.buttonFlashcardSubmit.setOnClickListener{
            userResponse = binding.editTextFlashcardUserResponse.text.toString()
            println("initial " + userResponse)
            binding.buttonFlashcardSubmit.isVisible = false
            binding.switchFlashcardAi.isVisible = false
            binding.editTextFlashcardUserResponse.isVisible = false
            if(binding.switchFlashcardAi.isChecked) {
                Log.d("Flashcard", binding.switchFlashcardAi.isChecked.toString())
//                val prompt =
//                    "In the prompt I give you, can you subsitute all spaces used for /. Here is the prompt $userResponse"
                val prompt = "Create a set of flash cards based on the text given and format it where every term and definition and term is seperated by a /. DO NOT add extra text and only place the words that you are given with a / to seperate the term and definition. For example, if you were given" +
                        " 'invertir   to invest     la bolsa de valores      stock market' then you would write invertir/to invest/la bolsa de valores/stock market. Here is the prompt $userResponse"
                  //  "Can you create a list for flashcards based on the given text and seperate each definition and term with a /" + userResponse
                MainScope().launch {
                    response = generativeModel.generateContent(prompt)
                    userResponse = response.text.toString()
                    quizValue = splitValue(userResponse)
                    println("userresponse " + userResponse)
                    binding.buttonFlashcardFlashcard.text = quizValue[0]
                    println("Final value: " + quizValue)
                    binding.textViewFlashcardNumber.text = "Flashcard: 1" + "/" + quizValue.size/2
                 //   binding.textViewFlashcardNumber.text = "Flashcard: 1" + "/ " + "Flashcard: " + flashcardNumber + "/ " + quizValue.size
                }
          //      quizValue = splitValue(userResponse)


            }
            else{
                quizValue = splitValue(userResponse)
                println("quiz value after " + quizValue)
                binding.buttonFlashcardFlashcard.text = quizValue[0]
            }
              //  quizValue = userResponse.split("/")
                println("quiz value" + quizValue)
                binding.buttonFlashcardFlashcard.isVisible = true
                binding.imageButtonFlashcardLeft.isVisible = true
                binding.imageButtonFlashcardRight.isVisible = true
                binding.textViewFlashcardDirection.isVisible = true
                binding.textViewFlashcardNumber.isVisible = true

                //   binding.textViewFlashcardDirection.text = "Front"
                binding.textViewFlashcardNumber.text = "Flashcard: 1" + "/" + quizValue.size/2

        }

//        if(quizIteration % 2 == 0){
//            binding.textViewFlashcardDirection.text = "Front"
//        }
//        else{
//            binding.textViewFlashcardDirection.text = "Back"
//        }


        binding.buttonFlashcardFlashcard.setOnClickListener {
            if (quizIteration % 2 == 0)
            {
                quizIteration ++
                binding.buttonFlashcardFlashcard.text = quizValue[quizIteration]
                binding.textViewFlashcardDirection.text = "Back"
                binding.textViewFlashcardNumber.text = "Flashcard: " + flashcardNumber + "/" + quizValue.size/2
            }
            else{
                quizIteration --
                binding.buttonFlashcardFlashcard.text = quizValue[quizIteration]
                binding.textViewFlashcardDirection.text = "Front"
            }


        }
        binding.imageButtonFlashcardRight.setOnClickListener{
            if (quizIteration % 2 == 0)
            {
                quizIteration += 2
                binding.buttonFlashcardFlashcard.text = quizValue[quizIteration]
                binding.textViewFlashcardDirection.text = "Front"
                flashcardNumber ++
                binding.textViewFlashcardNumber.text = "Flashcard: " + flashcardNumber + "/" + quizValue.size/2
            }
            else{
                quizIteration += 1
                binding.buttonFlashcardFlashcard.text = quizValue[quizIteration]
                binding.textViewFlashcardDirection.text = "Front"
                flashcardNumber ++
                binding.textViewFlashcardNumber.text = "Flashcard: " + flashcardNumber + "/" + quizValue.size/2
            }
        }
        binding.imageButtonFlashcardLeft.setOnClickListener{
            if (quizIteration % 2 == 0)
            {
                quizIteration -= 2
                binding.buttonFlashcardFlashcard.text = quizValue[quizIteration]
                binding.textViewFlashcardDirection.text = "Front"
                flashcardNumber --
                binding.textViewFlashcardNumber.text = "Flashcard: " + flashcardNumber + "/" + quizValue.size/2
            }
            else{
                quizIteration -= 3
                binding.buttonFlashcardFlashcard.text = quizValue[quizIteration]
                binding.textViewFlashcardDirection.text = "Front"
                flashcardNumber --
                binding.textViewFlashcardNumber.text = "Flashcard: " + flashcardNumber + "/" + quizValue.size/2
            }
        }
        binding.buttonFlashcardBack.setOnClickListener{
            finish()
        }



    }
    fun splitValue(userResponse: String): List<String> {
        return userResponse.split("/")

    }
}