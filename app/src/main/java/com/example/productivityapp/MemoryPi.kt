package com.example.productivityapp

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.productivityapp.databinding.ActivityMemoryPiBinding
import java.io.File
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

    private val regexPunct = Regex("(?<=[\\p{Punct} &&[^_]&&[^']])|(?=[\\p{Punct} &&[^_]&&[^']])")

    companion object {
        val TAG = "memoryPi"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoryPiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate: ${intent.getStringExtra(MemoryPiAdapter.EXTRA_FILENAME)}")

        //read the text and transfer to textView
        binding.textViewMemoryPiTextMemorize.text =
            try {
                this@MemoryPi.openFileInput(intent.getStringExtra(MemoryPiAdapter.EXTRA_FILENAME))
                    .bufferedReader().useLines { lines ->
                        lines.fold("") { some, text ->
                            "$some\n$text"
                        }
                    }
            } catch (e: FileNotFoundException) {
                Log.d(TAG, "onCreate: File DNE")

                getString(R.string.fillerText)
            }

        binding.editTextTextMemoryPiTitle.setText(
            intent.getStringExtra(MemoryPiAdapter.EXTRA_FILENAME).toString().removeSuffix(".txt")
        )

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
                    writeToStorage(newTextToMemorize, binding.editTextTextMemoryPiTitle.text.toString())
                    binding.textViewMemoryPiTextMemorize.text = newTextToMemorize

                    fullText = newTextToMemorize
                    fullTextSplit = fullText.split(regexPunct).toMutableList()
                    choppedFullText = fullTextSplit.toMutableList()
                }
            )
            builder.setNegativeButton("Cancel", null)

            builder.show()

        }


        // Add letters back to the text
        binding.buttonMemoryPiAddLetter.setOnClickListener {
            if (punctRemoved) {
                showPunctuation()
                punctRemoved = false
            } else if (numRemoved > 0) {
                numRemoved--
                //consider refactoring this code into its own method
                for (i in choppedFullText.indices) {
                    if (!choppedFullText[i].contains(regexPunct)) {
                        if (numRemoved <= fullTextSplit[i].length) {
                            choppedFullText[i] = fullTextSplit[i].substring(
                                0,
                                fullTextSplit[i].length - numRemoved
                            ) + "_".repeat(numRemoved)
                        }
                    }
                }

            }

            binding.textViewMemoryPiTextMemorize.text = choppedFullText.joinToString("")


        }

        //Remove letters from the text
        binding.buttonMemoryPiRemoveLetter.setOnClickListener {
            if (numRemoved < max) {
                numRemoved++

                for (i in choppedFullText.indices) {
                    if (!choppedFullText[i].contains(regexPunct)) {
                        if (numRemoved <= fullTextSplit[i].length) {
                            choppedFullText[i] = fullTextSplit[i].substring(
                                0,
                                fullTextSplit[i].length - numRemoved
                            ) + "_".repeat(numRemoved)
                        }
                    }
                }
            } else {
                hidePunctuation()
                punctRemoved = true
            }
            binding.textViewMemoryPiTextMemorize.text = choppedFullText.joinToString("")

        }

        binding.buttonMemoryPiBack.setOnClickListener {
            finish()
        }

        binding.editTextTextMemoryPiTitle.onFocusChangeListener =
            OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    //Do something when EditText has focus
                } else {
                    // Do something when Focus is not on the EditText
                    val worked = getFileStreamPath(
                        intent.getStringExtra(MemoryPiAdapter.EXTRA_FILENAME)
                            ?: throw IllegalArgumentException("No file found")
                    ).renameTo(File(filesDir.toString() + "/" + binding.editTextTextMemoryPiTitle.text.toString() + ".txt"))
                    Log.d(TAG, "onCreate: $worked")

                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(binding.editTextTextMemoryPiTitle.windowToken, 0)
                }


                Log.d(
                    TAG,
                    "onCreate: ${filesDir.toString() + "/" + binding.editTextTextMemoryPiTitle.text.toString()}.txt"
                )
            }

    }

    private fun writeToStorage(text: String, fileName: String) {
        this.openFileOutput("$fileName.txt", Context.MODE_PRIVATE).use {
            it.write(text.toByteArray())
        }
    }

    private fun hidePunctuation() {
        for (i in choppedFullText.indices) {
            if (choppedFullText[i].contains(regexPunct)) {
                choppedFullText[i] = "_"
            }
        }
    }

    private fun showPunctuation() {
        for (i in choppedFullText.indices) {
            if (fullTextSplit[i].contains(regexPunct)) {
                choppedFullText[i] = fullTextSplit[i]
            }
        }
    }


}
