package com.example.productivityapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.example.productivityapp.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var timer: CountDownTimer

    private var isTimerRunning = false

    private var timeLeftOnTimer = 0L
    private var timerTimeSec = 1500L


    companion object {
        val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonMainActivityQuiz.setOnClickListener{
            val intent = Intent(this, Flashcard::class.java)
            this.startActivity(intent)
        }
        binding.buttonMainActivityGradeCalc.setOnClickListener{
            val intent = Intent(this, GradeCalculator::class.java)
            this.startActivity(intent)
        }
        binding.buttonMainActivityMemoryPi.setOnClickListener{
            val intent = Intent(this, MemoryPiFileSelect::class.java)
            this.startActivity(intent)
        }

        binding.editTextTextMainEnterTimeMillis.onFocusChangeListener =
            View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    //Do something when EditText has focus
                } else {
                    // Do something when Focus is not on the EditText
                    if (binding.editTextTextMainEnterTimeMillis.text.toString().isDigitsOnly()) {
                        timerTimeSec = binding.editTextTextMainEnterTimeMillis.text.toString().toLong()*60
                        timer.cancel()
                        newTimer(timerTimeSec*1000)

                        val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                        imm?.hideSoftInputFromWindow(binding.editTextTextMainEnterTimeMillis.windowToken, 0)
                    }
                }
            }


        binding.textViewMainTime.text = formatTime()


        newTimer(timerTimeSec*1000)


        binding.buttonMainStart.setOnClickListener {
            if (!isTimerRunning) {
                isTimerRunning = true
                timer.start()
            }
            else {
                isTimerRunning = false
                timer.cancel()
                newTimer(timeLeftOnTimer)
                //timerTimeSec = timeLeftOnTimer/1000
            }
        }

        binding.buttonMainReset.setOnClickListener {
            isTimerRunning = false
            timer.cancel()
            newTimer(timerTimeSec*1000)
            binding.textViewMainTime.text = formatTime()
        }

    }

    fun newTimer(newMillisInFuture:Long) {
        timer = object : CountDownTimer(newMillisInFuture, 10) {
            override fun onTick(millisUntilFinished: Long) {
                val secTillFinish  = millisUntilFinished/1000
                binding.textViewMainTime.text = "${secTillFinish/3600}:${(secTillFinish%3600)/60}:${secTillFinish % 60}"
                timeLeftOnTimer = millisUntilFinished
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Break time", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun formatTime():String {
        return "${(timerTimeSec / 3600)}:${((timerTimeSec % 3600) / 60)}:${timerTimeSec % 60}"
    }

}