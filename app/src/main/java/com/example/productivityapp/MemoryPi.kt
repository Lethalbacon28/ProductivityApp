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
    private lateinit var fullTextSplit: MutableList<String>
    private lateinit var choppedFullText: MutableList<String>

    private var numRemoved = 0
    private var punctRemoved = false

    //val regexPunct = Regex("([?<=\\p{Punct}]|[?=\\p{Punct}])")

    val regexPunct = Regex("(?<=[\\p{Punct} \\n&&[^_]])|(?=[\\p{Punct} \\n&&[^_]])")

    companion object {
        val TAG = "memoryPi"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryPiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //read the text and transfer to textView
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

        //Initialize vars that hold different versions of the text
        fullText = binding.textViewMemoryPiTextMemorize.text.toString()
        fullTextSplit = fullText.split(regexPunct).toMutableList()
        choppedFullText = fullTextSplit.toMutableList()

        Log.d(TAG, "onCreate: $fullTextSplit")

        // create a max var that holds the max value of numRemoved
        var max = 0
        for (i in fullTextSplit.indices) {
            max = max(max, fullTextSplit[i].length)
        }


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

            fullText = newTextToMemorize
            fullTextSplit = fullText.split(regexPunct).toMutableList()
            choppedFullText = fullTextSplit.toMutableList() // copies the list
        }



        // Add letters back to the text
        binding.buttonMemoryPiAddLetter.setOnClickListener {
            if (punctRemoved) {
                showPunctuation()
                punctRemoved = false
                binding.textViewMemoryPiTextMemorize.text = choppedFullText.joinToString("")
            }
            else if (numRemoved > 0) {
                numRemoved--

                //consider refactoring this code into its own method
                for (i in choppedFullText.indices) {
                    if (!choppedFullText[i].contains(regexPunct) && choppedFullText[i]!="\n") {
                        if (numRemoved <= fullTextSplit[i].length) {
                            choppedFullText[i] = fullTextSplit[i].substring(
                                0,
                                fullTextSplit[i].length - numRemoved
                            ) + "_".repeat(numRemoved)
                        }
                    }
                }
                binding.textViewMemoryPiTextMemorize.text = choppedFullText.joinToString("")

            }

            Log.d(TAG, "onCreate: $numRemoved")

        }

        //Remove letters from the text
        binding.buttonMemoryPiRemoveLetter.setOnClickListener {
            if (numRemoved < max) {
                numRemoved++

                for (i in choppedFullText.indices) {
                    if (!choppedFullText[i].contains(regexPunct) && choppedFullText[i]!="\n") {
                        if (numRemoved <= fullTextSplit[i].length) {
                            choppedFullText[i] = fullTextSplit[i].substring(
                                0,
                                fullTextSplit[i].length - numRemoved
                            ) + "_".repeat(numRemoved)
                        }
                    }
                }
                //showPunctuation()
                //binding.textViewMemoryPiTextMemorize.text = choppedFullText.joinToString("")
            }
            else {
                hidePunctuation()
                punctRemoved = true
            }
            binding.textViewMemoryPiTextMemorize.text = choppedFullText.joinToString("")

            Log.d(TAG, "onCreate: $numRemoved")

        }

    }

    private fun writeToStorage(text: String) {
        val filename = getString(R.string.fileName)
        val fileContents = text
        this@MemoryPi.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }
    }

    // I dunno how to implement these
    // The idea is that after all normal letters are removed
    // All punctuation counts as a "final" letter and is removed
    private fun hidePunctuation() {
        for (i in choppedFullText.indices) {
            if (choppedFullText[i].contains(regexPunct) && choppedFullText[i]!="\n") {
                choppedFullText[i] = "_"
            }
        }
    }

    private fun showPunctuation() {
        Log.d(TAG, "showPunctuation: I ran")
        for (i in fullTextSplit.indices) {
            if (fullTextSplit[i].contains(regexPunct)) {
                choppedFullText[i] = fullTextSplit[i]
                Log.d(TAG, "showPunctuation: replaced ${choppedFullText[i]} with ${fullTextSplit[i]}")
            }
        }

    }



}