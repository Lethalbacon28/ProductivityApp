package com.example.productivityapp

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.productivityapp.databinding.ActivityMemoryPiBinding
import java.io.FileNotFoundException


class MemoryPi : AppCompatActivity() {

    private lateinit var binding: ActivityMemoryPiBinding

    private var newTextToMemorize = ""
    private lateinit var fullText: String
    private var numRemoved = 0

    companion object {
        val TAG = "memoryPi"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryPiBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.textViewMemoryPiTextMemorize.text =
            try {
            this@MemoryPi.openFileInput(getString(R.string.fileName)).bufferedReader()
                .useLines { lines ->
                    lines.fold("") { some, text ->
                        "$some\n$text"
                    }
                }
        } catch (e: FileNotFoundException) {
            Log.d(TAG, "onCreate: File DNE")

            "Hello! This is some filler text. Try pressing the add and remove letter buttons!"
        }

        fullText = binding.textViewMemoryPiTextMemorize.text.toString()


        binding.buttonMemoryPiEditText.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Enter New Text to Memorize")

            val input = EditText(this)

            builder.setView(input)

            builder.setPositiveButton("Save",
                DialogInterface.OnClickListener { thing, which ->
                    newTextToMemorize = input.text.toString()
                    writeToStorage(newTextToMemorize)
                    binding.textViewMemoryPiTextMemorize.text = newTextToMemorize

                }
            )
            builder.setNegativeButton("Cancel", null)

            builder.show()
        }

        binding.buttonMemoryPiAddLetter.setOnClickListener {
            var tempText =


        }

        binding.buttonMemoryPiRemoveLetter.setOnClickListener {
            var tempText = fullText.split(" ").toMutableList()
            for (i in 0 until tempText.size) {
                tempText[i] = (tempText[i].substring(0, tempText[i].length - 2))
                numRemoved++;
            }
        }


    }

    private fun writeToStorage(text: String) {
        val filename = getString(R.string.fileName)
        val fileContents = text
        this@MemoryPi.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }
    }




}