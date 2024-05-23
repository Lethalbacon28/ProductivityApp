package com.example.productivityapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.productivityapp.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mydir: File = this.getDir(getString(R.string.fileDir), MODE_PRIVATE) //Creating an internal dir;
        if (!mydir.exists()) {
            mydir.mkdirs()
            Log.d("what", "onCreate: Directory made")
        }else {
            Log.d("what", "onCreate: already made")
        }


        //Log.d("TAG", "onCreate: I ran")


        //Log.d("onCreate: ", "onCreate: ${this@MainActivity.fileList()}")
        //binding.chronometerMainStopwatch.isCountDown = true

        this.startActivity(Intent(this, MemoryPiFileSelect::class.java))
//
//        binding.buttonMainActivityQuiz.setOnClickListener{
//            val intent = Intent(this, QuizActivity::class.java)
//            this.startActivity(intent)
//        }
//    // we haven't done any of this yet lmao
////        binding.buttonMainChronometer.setOnClickListener{
////            val intent = Intent(this, ::class.java)
////            this.startActivity(intent)
////        }
//        binding.buttonMainActivityGradeCalc.setOnClickListener{
//            val intent = Intent(this, GradeCalculator::class.java)
//            this.startActivity(intent)
//        }
//        binding.buttonMainActivityMemoryPi.setOnClickListener{
//            val intent = Intent(this, MemoryPi::class.java)
//            this.startActivity(intent)
//        }
//
//
////        val intent = Intent(this, MemoryPi::class.java)
////        this.startActivity(intent)

    }
}