package com.example.productivityapp

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productivityapp.databinding.ActivityMemoryPiFileSelectBinding
import java.io.File


class MemoryPiFileSelect : AppCompatActivity() {

    private lateinit var binding: ActivityMemoryPiFileSelectBinding

    private lateinit var adapter: MemoryPiAdapter

    private lateinit var files: MutableList<File>

    //private var fileListName = mutableListOf<String>()

    companion object {
        val TAG = "MemoryPiFileSelect"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryPiFileSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resetList()

        binding.floatingActionButtonMemoryPiFileSelectNewFile.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Enter Title of New Text")

            val input = EditText(this)

            builder.setView(input)

            builder.setPositiveButton("Save", DialogInterface.OnClickListener {thing, which ->

                this.openFileOutput(input.text.toString() + ".txt", Context.MODE_PRIVATE).use {
                    it.write(getString(R.string.fillerText).toByteArray())
                }

                Log.d(TAG, "onCreate: ${getFileStreamPath(input.text.toString()+".txt")}")
                adapter.fileList.add(getFileStreamPath(input.text.toString()+".txt"))
                adapter.notifyDataSetChanged()
            })

            builder.setNegativeButton("Cancel", null)

            builder.show()
        }

        binding.buttonMemoryPiFileSelectBack.setOnClickListener {
            finish()
        }

        binding.floatingActionButtonMemoryPiFileSelectRefresh.setOnClickListener {
            resetList()
        }

        binding.imageView.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("How this works")

            builder.setMessage("Click on a text to open it and start memorizing! \n\n" +
                    "The refresh button (bottom right): Manually refreshes the list if something hasn\'t updated \n\n" +
                    "The pencil button: Create a new text to memorize")

            builder.setPositiveButton("Okay",null)

            builder.show()
        }

    }

    fun resetList() {
        files = filesDir.listFiles().filter { it.isFile && it.canRead() && it.name.endsWith(".txt") }
            .toMutableList()

        adapter = MemoryPiAdapter(files)
        binding.recyclerViewMemoryPiFileSelectFileList.adapter = adapter
        binding.recyclerViewMemoryPiFileSelectFileList.layoutManager = LinearLayoutManager(this)
    }

}