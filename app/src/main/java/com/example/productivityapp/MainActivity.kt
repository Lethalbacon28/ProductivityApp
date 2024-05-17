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

        val mydir: File = this.getDir("ProductivityApp", MODE_PRIVATE) //Creating an internal dir;

        if (!mydir.exists()) {
            mydir.mkdirs()
        }

        Log.d("Something", "onCreate: ${mydir.listFiles()}")

        //binding.chronometerMainStopwatch.isCountDown = true

        val intent = Intent(this, MemoryPi::class.java)
        this.startActivity(intent)

    }
}