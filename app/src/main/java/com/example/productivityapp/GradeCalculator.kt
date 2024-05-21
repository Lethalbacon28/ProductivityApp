package com.example.productivityapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.productivityapp.databinding.ActivityGradeCalculatorBinding

class GradeCalculator : AppCompatActivity() {

    private lateinit var binding: ActivityGradeCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGradeCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGradeCalculatorCalculate.setOnClickListener{

            val currentGrade = if(binding.editTextGradeCalculatorCurrentGrade.text.toString() != "") binding.editTextGradeCalculatorCurrentGrade.text.toString().toDouble() else 0.0
            val gradeWanted = if(binding.editTextGradeCalculatorGradeWanted.text.toString() != "") binding.editTextGradeCalculatorGradeWanted.text.toString().toDouble() else 0.0
            val finalWorth = if(binding.editTextGradeCalculatorFinalGradeWorth.text.toString() != "") binding.editTextGradeCalculatorFinalGradeWorth.text.toString().toDouble() else 0.0

            if(currentGrade == 0.0 || gradeWanted == 0.0 || finalWorth == 0.0) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Please enter all fields correctly before calculating your grade.")
                builder.setPositiveButton("Ok", null)
                builder.show()
            }
            else {
                val letterGradeWanted = when(gradeWanted.toInt()) {
                    in 90..100 -> "an A"
                    in 80 until 90 -> "a B"
                    in 70 until 80 -> "a C"
                    in 60 until 70 -> "a D"
                    else -> "a F"
                }
                val gradeNeeded = String.format("%.2f", (gradeWanted-currentGrade*(1-finalWorth/100))/(finalWorth/100))
                val builder = AlertDialog.Builder(this)
                builder.setTitle("You need $gradeNeeded% to get $letterGradeWanted.")
                builder.setPositiveButton("Ok", null)
                builder.show()
            }
        }
        binding.buttonGradeCalculatorBack.setOnClickListener{
            finish()
        }
    }
}