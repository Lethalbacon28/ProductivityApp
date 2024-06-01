package com.example.productivityapp

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.productivityapp.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var timerLength = 0
    var tickLength = 1000


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //binding.chronometerMainStopwatch.isCountDown = true


        binding.buttonMainActivityQuiz.setOnClickListener{
            val intent = Intent(this, Flashcard::class.java)
            this.startActivity(intent)
        }
    // we haven't done any of this yet lmao
//        binding.buttonMainChronometer.setOnClickListener{
//            val intent = Intent(this, Flashcard::class.java)
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



//if you guys wanna do this
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                TODO("Not yet implemented")
            }

            override fun onFinish() {
                TODO("Not yet implemented")
            }
        }
    }
}