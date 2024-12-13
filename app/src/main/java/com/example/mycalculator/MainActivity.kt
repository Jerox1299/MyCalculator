package com.example.mycalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var num1 = 0.0
    private var num2 = 0.0
    private var operation = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupButtonListeners()
    }

    private fun setupButtonListeners() {
        val numberButtons = listOf(
            binding.btn0,
            binding.btn1,
            binding.btn2,
            binding.btn3,
            binding.btn4,
            binding.btn5,
            binding.btn6,
            binding.btn7,
            binding.btn8,
            binding.btn9,
            binding.btnPunto
        )

        for (button in numberButtons) {
            button.setOnClickListener { clicNumero(it) }
        }

        val operationButtons = listOf(
            binding.btnSumar, binding.btnRestar, binding.btnMult, binding.btnDividir
        )

        for (button in operationButtons) {
            button.setOnClickListener { clicOperacion(it) }
        }

        binding.btnIgual.setOnClickListener { calcularResultado() }
        binding.btnBorrar.setOnClickListener { borrar() }
        binding.btnBorrarUltimo.setOnClickListener { borrarUltimo() }
    }

    private fun clicNumero(view: View) {
        val numero = (view as Button).text.toString()
        if (operation.isEmpty()) {
            // Si el botón de número es '-', lo tratamos como un negativo si el campo está vacío
            if (numero == "-" && binding.tvNum1.text.isEmpty()) {
                binding.tvNum1.append("-")
            } else {
                binding.tvNum1.append(numero)
            }
        } else {
            if (numero == "-" && binding.tvNum2.text.isEmpty()) {
                binding.tvNum2.append("-")
            } else {
                binding.tvNum2.append(numero)
            }
        }
    }

    private fun clicOperacion(view: View) {
        val oper = (view as Button).text.toString()
        if (operation.isEmpty() && binding.tvNum1.text.isNotEmpty()) {
            operation = oper
            num1 = binding.tvNum1.text.toString().toDoubleOrNull() ?: 0.0
        }
    }

    private fun calcularResultado() {
        num2 = binding.tvNum2.text.toString().toDoubleOrNull() ?: 0.0
        val resultado = when (operation) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> if (num2 != 0.0) num1 / num2 else Double.NaN
            else -> 0.0
        }
        binding.tvNum1.text = if (resultado % 1 == 0.0) {
            resultado.toInt().toString() // Mostrar como entero si no tiene decimales
        } else {
            resultado.toString()
        }
        binding.tvNum2.text = ""
        operation = ""
    }

    private fun borrar() {
        binding.tvNum1.text = ""
        binding.tvNum2.text = ""
        num1 = 0.0
        num2 = 0.0
        operation = ""
    }

    private fun borrarUltimo() {
        if (operation.isEmpty()) {
            binding.tvNum1.text = binding.tvNum1.text.toString().dropLast(1)
        } else {
            binding.tvNum2.text = binding.tvNum2.text.toString().dropLast(1)
        }
    }
}

