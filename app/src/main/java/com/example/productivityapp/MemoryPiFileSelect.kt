package com.example.productivityapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productivityapp.databinding.ActivityMemoryPiFileSelectBinding
import java.util.Collections

class MemoryPiFileSelect : AppCompatActivity() {

    private lateinit var binding: ActivityMemoryPiFileSelectBinding

    private var fileListName = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryPiFileSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        this.getDir(getString(R.string.fileDir), Context.MODE_PRIVATE).apply {
//            Log.d("TAG", "onCreate: ${listFiles().size}")
//            val files = listFiles()
//            for (i in files.indices) {
//                Log.d("MemoryPiFileSelect", "onCreate: ${files[i].name?: "null"}")
//                fileListName.add(files[i].name)
//            }
//        }
//        fileListName.add(0,"hi")


        var files = filesDir.listFiles()
        files = files.filter { it.isFile && it.canRead() && it.name.endsWith(".txt") }.toTypedArray()


        val adapter = MemoryPiAdapter(files)
        binding.recyclerViewMemoryPiFileSelectFileList.adapter = adapter
        binding.recyclerViewMemoryPiFileSelectFileList.layoutManager = LinearLayoutManager(this)



    }
}