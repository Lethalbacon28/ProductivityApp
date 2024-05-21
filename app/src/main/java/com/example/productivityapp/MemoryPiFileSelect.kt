package com.example.productivityapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.productivityapp.databinding.ActivityMemoryPiFileSelectBinding

class MemoryPiFileSelect : AppCompatActivity() {

    private lateinit var binding: ActivityMemoryPiFileSelectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryPiFileSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.getDir(getString(R.string.fileDir), Context.MODE_PRIVATE).apply {
            val fileListName : MutableList<String>
            val files = listFiles()
            for (i in files.indices) {
                fileListName.add(files[i])
            }
        }




    }
}