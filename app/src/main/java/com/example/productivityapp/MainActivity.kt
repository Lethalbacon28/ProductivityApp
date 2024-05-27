package com.example.productivityapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.productivityapp.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //binding.chronometerMainStopwatch.isCountDown = true


        binding.buttonMainActivityQuiz.setOnClickListener{
            val intent = Intent(this, QuizActivity::class.java)
            this.startActivity(intent)
        }
    // we haven't done any of this yet lmao
//        binding.buttonMainChronometer.setOnClickListener{
//            val intent = Intent(this, ::class.java)
//            this.startActivity(intent)
//        }
        binding.buttonMainActivityGradeCalc.setOnClickListener{
            val intent = Intent(this, GradeCalculator::class.java)
            this.startActivity(intent)
        }
        binding.buttonMainActivityMemoryPi.setOnClickListener{
            val intent = Intent(this, MemoryPiFileSelect::class.java)
            this.startActivity(intent)
        }


    }
}