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
import kotlin.math.max


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
        Log.d(TAG, "onCreate: $fullText")

        val fullTextSplit = fullText.split(" ").toMutableList()
        Log.d(TAG, "onCreate: $fullTextSplit")

        var max = 0

        for (i in fullTextSplit.indices) {
            max = max(max, fullTextSplit[i].length)
        }

        val choppedFullText = fullTextSplit


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
            for (i in 0 until choppedFullText.size) {
                if (numRemoved <= fullTextSplit[i].length)
                    choppedFullText[i] = fullTextSplit[i].substring(0,numRemoved+1) + "_".repeat(numRemoved+1)
                }
            if (numRemoved >= 0) {
                numRemoved--
            }

            binding.textViewMemoryPiTextMemorize.text = choppedFullText.joinToString(" ")


        }

        binding.buttonMemoryPiRemoveLetter.setOnClickListener {
            if (numRemoved <= max) {
                numRemoved++
            }

            for (i in 0 until choppedFullText.size) {
                if (numRemoved <= choppedFullText[i].length) {
                    choppedFullText[i].replaceRange(
                        choppedFullText[i].length - numRemoved - 1,
                        choppedFullText[i].length,
                        "_".repeat(numRemoved + 1)
                    )
                }
            }


            binding.textViewMemoryPiTextMemorize.text = choppedFullText.joinToString(" ")

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