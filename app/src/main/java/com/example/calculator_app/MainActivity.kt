package com.example.calculator_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.core.text.isDigitsOnly
import com.example.calculator_app.databinding.CalculatorBinding
import java.util.logging.Logger.global
import kotlin.math.pow


class MainActivity : ComponentActivity() {
    private lateinit var binding: CalculatorBinding
    val global = Global()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CalculatorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    class Stack<T : Any>(val stack: ArrayDeque<T> = ArrayDeque(mutableListOf<T>())) {
        fun push(element: T) = stack.addLast(element)
        fun pop() = stack.removeLast()
        fun top() = stack.last()
        fun isEmpty() = stack.isEmpty()
    }

    class Global(private var openingParenthCt: Int = 0, private var canPlaceDecimal: Boolean = true){
        fun editOpeningParenthCt(operation: String){
            when(operation){
                "+" -> openingParenthCt++
                else -> openingParenthCt--
            }
        }
        fun getOpeningParenthCt() = openingParenthCt
        fun setCanPlaceDecimal(){
            canPlaceDecimal = !canPlaceDecimal
        }
        fun getCanPlaceDecimal() = canPlaceDecimal
        fun reset(options: List<String>) {
            if("parenthCt" in options){
                openingParenthCt = 0
            }
            if("decimalBool" in options){
                canPlaceDecimal = true
            }
        }
    }

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    fun operationAction(view: View) {
        val workView = binding.working
        if (view is Button) {
            val opSign = view.text
            if (workView.text.isNotEmpty()) {
                if (workView.text.last().isDigit() || workView.text.last() == '.') {
                    workView.append(opSign)
                    global.setCanPlaceDecimal()
                }
                else {
                    if (workView.text.length == 1) {
                        if (workView.text.last() != '-' && opSign == "-") {
                            if(workView.text.last() == '+') workView.text = opSign else workView.append(opSign)
                        }
                        else {
                            if(workView.text.last() != '^') workView.text = opSign
                        }
                    }
                    else {
                        if (workView.text.last() == '-' && !workView.text[workView.text.lastIndex - 1].isDigit()) {
                            if (opSign != "-")
                                workView.text = workView.text.substring(0, workView.text.length - 1)
                        }
                        else if (opSign == "-") {
                            if (workView.text.last() != '-')
                                workView.append(opSign)
                        }
                        else {
                            workView.text = workView.text.replaceRange(
                                workView.text.lastIndex,
                                workView.text.lastIndex + 1, opSign
                            )
                        }
                    }
                }
            }
            else {
                if (opSign == "-") workView.append(opSign)
            }
        }
    }

    // Names for the following two functions cannot change since they are specifically called on by
// the
    // As it is right now, even if the amount of numbers being added exceeds the size of the TextView,
    // they are still being appended onto the text of the TextView (just not being shown)
    fun numberAction(view: View) {
        if (view is Button) {
            val num = view.text.isDigitsOnly()
            if (num) {
                binding.working.append(view.text)
            }
            else{
                //filler
            }
        }

    }

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    fun operationAction(view: View) {
        val workingText: CharSequence = binding.working.text
        if(view is Button){
            val opSign = view.text
            val workView = binding.working
            if(workingText.isNotEmpty()){
                if(workingText.last().isDigit()){
                    workView.append(opSign)
                }
                else{
                    if(workingText.last() == '-' && !workingText[workingText.lastIndex-1].isDigit()){
                        if(opSign != "-")
                        binding.working.text = workingText.substring(0, workingText.length - 1)
                    }
                    else if(opSign == "-"){
                        if(workingText.last() != '-')
                            workView.append(opSign)
                    }
                    else{
                        workView.text = workView.text.replaceRange(
                            workView.text.lastIndex,
                            workView.text.lastIndex + 1, opSign)
                    }
                }
            }
            else{
                if (opSign == "-") workView.append(opSign)
            }
        }
    }

    // Clear the data in the results and working TextViews
    fun clear(view: View) {
        binding.results.text = ""
        binding.working.text = ""
    }

    // Erase one char from the charSequence in @id/display
    fun delete(view: View) {
        val displayChars: CharSequence = binding.working.text
        if (displayChars.isNotEmpty()) binding.working.text =
            displayChars.substring(0, displayChars.length - 1)
    }


}
