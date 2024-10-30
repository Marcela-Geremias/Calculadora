package com.example.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentInput = ""
    private var operator = ""
    private var firstNumber = 0.0
    private var shouldResetInput = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttons = listOf<Button>(
            findViewById(R.id.btn0), findViewById(R.id.btn1), findViewById(R.id.btn2),
            findViewById(R.id.btn3), findViewById(R.id.btn4), findViewById(R.id.btn5),
            findViewById(R.id.btn6), findViewById(R.id.btn7), findViewById(R.id.btn8),
            findViewById(R.id.btn9), findViewById(R.id.btnDecimal)
        )

        buttons.forEach { button ->
            button.setOnClickListener { onNumberClick(button.text.toString()) }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperatorClick("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { onOperatorClick("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperatorClick("×") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperatorClick("÷") }

        findViewById<Button>(R.id.btnClear).setOnClickListener { clearDisplay() }
        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }
    }

    private fun onNumberClick(value: String) {
        if (shouldResetInput) {
            currentInput = ""
            shouldResetInput = false
        }
        currentInput += value
        display.text = currentInput
    }

    private fun onOperatorClick(op: String) {
        if (currentInput.isNotEmpty()) {
            if (operator.isNotEmpty()) {
                calculateResult()  // Calcula o resultado acumulado antes de definir o novo operador
            }
            firstNumber = currentInput.toDouble()
            operator = op
            shouldResetInput = true
        }
    }

    private fun calculateResult() {
        if (currentInput.isNotEmpty() && operator.isNotEmpty()) {
            val secondNumber = currentInput.toDouble()
            val result = when (operator) {
                "+" -> firstNumber + secondNumber
                "-" -> firstNumber - secondNumber
                "×" -> firstNumber * secondNumber
                "÷" -> if (secondNumber != 0.0) firstNumber / secondNumber else {
                    display.text = "Error"
                    return
                }
                else -> return
            }
            display.text = result.toString()
            firstNumber = result // Armazena o resultado para operações subsequentes
            currentInput = ""
            operator = ""
            shouldResetInput = true
        }
    }

    private fun clearDisplay() {
        currentInput = ""
        operator = ""
        firstNumber = 0.0
        display.text = "0"
    }
}
