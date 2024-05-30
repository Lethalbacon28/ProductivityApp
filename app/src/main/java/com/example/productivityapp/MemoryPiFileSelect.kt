package com.example.productivityapp

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productivityapp.databinding.ActivityMemoryPiFileSelectBinding


class MemoryPiFileSelect : AppCompatActivity() {

    private lateinit var binding: ActivityMemoryPiFileSelectBinding

    private lateinit var adapter: MemoryPiAdapter

    //private var fileListName = mutableListOf<String>()

    companion object {
        val TAG = "MemoryPiFileSelect"
    }

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


        val files = filesDir.listFiles().filter { it.isFile && it.canRead() }
            .toMutableList()


        adapter = MemoryPiAdapter(files)
        binding.recyclerViewMemoryPiFileSelectFileList.adapter = adapter
        binding.recyclerViewMemoryPiFileSelectFileList.layoutManager = LinearLayoutManager(this)

        binding.floatingActionButtonMemoryPiFileSelectNewFile.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Enter Title of New Text")

            val input = EditText(this)

            builder.setView(input)

            builder.setPositiveButton("Save", DialogInterface.OnClickListener {thing, which ->
                //Log.d(TAG, "onCreate: ${input.text}")

                this.openFileOutput(input.text.toString() + ".txt", Context.MODE_PRIVATE).use {
                    it.write(getString(R.string.fillerText).toByteArray())
                }

                Log.d(TAG, "onCreate: ${getFileStreamPath(input.text.toString()+".txt")}")
                adapter.fileList.add(getFileStreamPath(input.text.toString()+".txt"))
                //adapter.notifyItemInserted(adapter.fileList.size)
                adapter.notifyDataSetChanged()
            })

            builder.setNegativeButton("Cancel", null)

            builder.show()
        }

        binding.buttonMemoryPiFileSelectBack.setOnClickListener {
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}