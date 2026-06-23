package com.ayanfe.scientificcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.toRadians
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private var currentInput = ""
    private var operand1 = 0.0
    private var currentOperator = ""
    private var isOperatorClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)

// Number buttons
        val numberButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        )
        for (id in numberButtons) {
            findViewById<Button>(id).setOnClickListener { view ->
                val button = view as Button
                if (isOperatorClicked) {
                    currentInput = ""
                    isOperatorClicked = false
                }
                currentInput += button.text.toString()
                tvDisplay.text = currentInput
            }
        }

// Operator buttons
        val operatorButtons = listOf(R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply, R.id.btnDivide)
        for (id in operatorButtons) {
            findViewById<Button>(id).setOnClickListener { view ->
                val button = view as Button
                if (currentInput.isNotEmpty()) {
                    operand1 = currentInput.toDoubleOrNull() ?: 0.0
                    currentOperator = button.text.toString()
                    isOperatorClicked = true
                }
            }
        }

// Equals button
        findViewById<Button>(R.id.btnEqual).setOnClickListener {
            if (currentInput.isNotEmpty() && currentOperator.isNotEmpty()) {
                val operand2 = currentInput.toDoubleOrNull() ?: 0.0
                val result = when (currentOperator) {
                    "+" -> operand1 + operand2
                    "-" -> operand1 - operand2
                    "*" -> operand1 * operand2
                    "/" -> if (operand2 != 0.0) operand1 / operand2 else Double.NaN
                    else -> 0.0
                }
                tvDisplay.text = result.toString()
                currentInput = result.toString()
                currentOperator = ""
            }
        }

// Clear button
        findViewById<Button>(R.id.btnClear).setOnClickListener {
            currentInput = ""
            operand1 = 0.0
            currentOperator = ""
            tvDisplay.text = "0"
        }

// Delete button
        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput = currentInput.dropLast(1)
                tvDisplay.text = if (currentInput.isEmpty()) "0" else currentInput
            }
        }

// Scientific buttons
        val scientificButtons = listOf(R.id.btnSin, R.id.btnCos, R.id.btnTan, R.id.btnSqrt)
        for (id in scientificButtons) {
            findViewById<Button>(id).setOnClickListener { view ->
                val button = view as Button
                if (currentInput.isNotEmpty()) {
                    val number = currentInput.toDoubleOrNull() ?: 0.0
                    val result = when (button.text.toString()) {
                        "sin" -> sin(toRadians(number))
                        "cos" -> {
                            cos(toRadians(number))
                        }
                        "tan" -> tan(toRadians(number))
                        "√" -> if (number >= 0) sqrt(number) else Double.NaN
                        else -> 0.0
                    }
                    tvDisplay.text = result.toString()
                    currentInput = result.toString()
                }
            }
        }
    }
}
