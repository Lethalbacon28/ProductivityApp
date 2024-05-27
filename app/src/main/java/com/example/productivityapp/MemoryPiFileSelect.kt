package com.example.productivityapp

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productivityapp.databinding.ActivityMemoryPiFileSelectBinding


class MemoryPiFileSelect : AppCompatActivity() {

    private lateinit var binding: ActivityMemoryPiFileSelectBinding

    //private var fileListName = mutableListOf<String>()

    companion object {
        //val EXTRA_FILENAME = "nameOfNewFile"
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


        val files = filesDir.listFiles().filter { it.isFile && it.canRead() && it.name.endsWith(".txt") }
            .toMutableList()


        val adapter = MemoryPiAdapter(files)
        binding.recyclerViewMemoryPiFileSelectFileList.adapter = adapter
        binding.recyclerViewMemoryPiFileSelectFileList.layoutManager = LinearLayoutManager(this)

        binding.floatingActionButtonMemoryPiFileSelectNewFile.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Enter Title of New Text")

            val input = EditText(this)

            builder.setView(input)

            builder.setPositiveButton("Save", DialogInterface.OnClickListener {thing, which ->
                this.openFileOutput(input.text.toString() + ".txt", Context.MODE_PRIVATE).use {
                    it.write(getString(R.string.fillerText).toByteArray())
                }
                adapter.fileList.add(getDir(input.text.toString()+".txt".substring(4), Context.MODE_PRIVATE))
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
}