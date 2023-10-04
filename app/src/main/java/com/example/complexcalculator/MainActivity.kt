package com.example.complexcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.complexcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //This string is used to store the calculation expression
    var calString = ""
    //Used to store the first operand1
    private var operand1: Double = 0.0
    //Used to store the currently used operator
    private var currentOperator: Char? = null
    //Used to store the final result of the calcualtion
    private var result: Double = 0.0
    //private var operand2: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Implementation of onClickListeners for all the buttons
        binding.seven.setOnClickListener {
            setInputText(appendNumber(7))
        }

        binding.eight.setOnClickListener {
            setInputText(appendNumber(8))
        }

        binding.nine.setOnClickListener {
            setInputText(appendNumber(9))
        }

        binding.four.setOnClickListener {
            setInputText(appendNumber(4))
        }

        binding.five.setOnClickListener {
            setInputText(appendNumber(5))
        }

        binding.six.setOnClickListener {
            setInputText(appendNumber(6))
        }

        binding.one.setOnClickListener {
            setInputText(appendNumber(1))
        }

        binding.two.setOnClickListener {
            setInputText(appendNumber(2))
        }

        binding.three.setOnClickListener {
            setInputText(appendNumber(3))
        }

        binding.decimal.setOnClickListener {
            //This condition prevents two decimals to be entered simultaneously or even within one number
            if(calString.isNotEmpty() && calString.contains('.')){
            }else{
                setInputText(appendChar('.'))
            }
        }

        binding.zero.setOnClickListener {
            setInputText(appendNumber(0))
        }

        binding.plus.setOnClickListener {
            if(calString == ""){
                //This condition prevents pressing of plus button in absence of any input
                Toast.makeText(this, "Invalid format used", Toast.LENGTH_LONG).show()
            } else if(calString[calString.length-1] =='+'){
                //This condition prevents addition of twp plus operators simultaneously
            }
            else if(binding.oldInput.text.toString().isNotEmpty() && binding.inputDisplay.text.toString().isNotEmpty() && currentOperator != null && result == 0.0){
                //This condition causes the calculator to work in the way it's supposed to on the pressing of '+' button after the '=' button has been pressed
                binding.oldInput.text = operand1.toString()
                setOperator('+')
                binding.inputDisplay.text = ""
                calString = ""
            }
            else if(calString[calString.length-1] =='-' || calString[calString.length-1] =='X' || calString[calString.length-1] =='÷'){
                //Log.d("MainActivity.multiply", "Working else if 2")
                //Replaces the operator
                calString = calString.dropLast(1)
                setOperand1(calString)
                setOperator('+')
                setCalcText(appendChar('+'))
            } else{
                Log.d("MainActivity.multiply", "Working else")
                setOperand1(calString)
                setOperator('+')
                setCalcText(appendChar('+'))
            }

        }

        binding.minus.setOnClickListener {
            if(calString == ""){
                Toast.makeText(this, "Invalid format used", Toast.LENGTH_LONG).show()
            }else if(calString[calString.length-1] =='-'){
            }else if(binding.oldInput.text.toString().isNotEmpty() && binding.inputDisplay.text.toString().isNotEmpty() && currentOperator != null && result == 0.0){
                binding.oldInput.text = operand1.toString()
                setOperator('-')
                binding.inputDisplay.text = ""
                calString = ""
            }
            else if(calString[calString.length-1] =='+' || calString[calString.length-1] =='X' || calString[calString.length-1] =='÷'){
                calString = calString.dropLast(1)
                setOperand1(calString)
                setOperator('-')
                setCalcText(appendChar('-'))
            }
            else{
                setOperand1(calString)
                setOperator('-')
                setCalcText(appendChar('-'))
            }
        }

        binding.multiply.setOnClickListener {
            if(calString == ""){
                Toast.makeText(this, "Invalid format used", Toast.LENGTH_LONG).show()
            } else if(calString[calString.length-1] =='X'){
            } else if(binding.oldInput.text.toString().isNotEmpty() && binding.inputDisplay.text.toString().isNotEmpty() && currentOperator != null && result == 0.0){
                binding.oldInput.text = operand1.toString()
                setOperator('X')
                binding.inputDisplay.text = ""
                calString = ""
            }
            else if(calString[calString.length-1] =='-' || calString[calString.length-1] =='X' || calString[calString.length-1] =='÷'){

                calString = calString.dropLast(1)
                setOperand1(calString)
                setOperator('X')
                setCalcText(appendChar('X'))
            }
            else{
                setOperand1(calString)
                setOperator('X')
                setCalcText(appendChar('X'))
            }
        }

        binding.divide.setOnClickListener {
            if(calString == ""){
                Toast.makeText(this, "Invalid format used", Toast.LENGTH_LONG).show()
            }else if(calString[calString.length-1] =='÷'){
            }else if(binding.oldInput.text.toString().isNotEmpty() && binding.inputDisplay.text.toString().isNotEmpty() && currentOperator != null && result == 0.0){
                binding.oldInput.text = operand1.toString()
                setOperator('÷')
                binding.inputDisplay.text = ""
                calString = ""
            }
            else if(calString[calString.length-1] =='-' || calString[calString.length-1] =='X' || calString[calString.length-1] =='+'){
                calString = calString.dropLast(1)
                setOperand1(calString)
                setOperator('÷')
                setCalcText(appendChar('÷'))
            }
            else{
                setOperand1(calString)
                setOperator('÷')
                setCalcText(appendChar('÷'))
            }
        }

        binding.clear.setOnClickListener {
            clearAll()
        }

        binding.backspace.setOnClickListener {
            if(calString.isNotEmpty()){
                calString = calString.dropLast(1)
                setInputText(calString)
            }else if(calString.isEmpty() && currentOperator != null){
                currentOperator = null
                binding.operatorDisplay.text = ""
                binding.oldInput.text = binding.oldInput.text.toString().dropLast(1)
            } else if (calString.isEmpty() && currentOperator == null && binding.oldInput.text.toString().isNotEmpty()){
                binding.oldInput.text = binding.oldInput.text.toString().dropLast(1)
            }
        }

        binding.equals.setOnClickListener {
            if(binding.oldInput.text.toString().isNotEmpty() && currentOperator != null && binding.inputDisplay.text.toString().isNotEmpty()){
                calculateResult()
            }

        }

    }

    private fun appendNumber(number: Int) : String{
        calString += number
        return calString
    }

    private fun appendChar(ch: Char) : String{
        calString += ch
        return calString
    }

    private fun setInputText(input: String){
        binding.inputDisplay.text = input
    }

    private fun setOperator(op: Char){
        currentOperator = op
        binding.operatorDisplay.text = op.toString()
        Log.d("MainActivity.setOperator", "Working operator setting")
    }

    private fun setCalcText(input: String){
        binding.oldInput.text = calString
        clearInput()
    }

    private fun clearInput() {
        binding.inputDisplay.text = ""
        calString = ""
    }

    private fun clearAll(){
        binding.inputDisplay.text = ""
        binding.oldInput.text = ""
        binding.operatorDisplay.text = ""
        calString = ""
        operand1 = 0.0
        currentOperator = null
        result = 0.0
    }

    private fun setOperand1(string: String){
        Log.d("MainActivity.setOperand1", "Working operand setting")
        operand1 = string.toDouble()
    }

    private fun calculateResult(){
        var operand2: Double = binding.inputDisplay.text.toString().toDouble()
        if (currentOperator == '+'){
            result = operand1 + operand2
        } else if(currentOperator == '-'){
            result = operand1 - operand2
        } else if(currentOperator == 'X'){
            result = operand1 * operand2
        } else if (currentOperator == '÷'){
            if (operand2.toInt() != 0){
                result = operand1/operand2
            }
        }

        // Display the result and reset the state
        if (result != null) {
            binding.oldInput.text = ("${operand1}${(currentOperator).toString()}${operand2}").toString()
            binding.inputDisplay.text = result.toString()
            operand1 = result
            operand2 = 0.0
            result = 0.0
        }
    }
}