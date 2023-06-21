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
    // The buttons here are numbers 0-9 as well as '=' and '.'
    @SuppressLint("SetTextI18n")
    fun numberAction(view: View) {
        val idWorking = binding.working
        val idResults = binding.results
        if (view is Button) {
            when (view.text) {
                "=" -> {
                    if(idWorking.text.isNotEmpty()){
                        if (idWorking.text.last().isDigit() || idWorking.text.last() == '.' || idWorking.text.last() == ')'){
                            Log.i("infix", idWorking.text.toString())
                            idResults.text = infix()
                        }
                        else{
                            //Log.i("e", idWorking.text.last().toString())
                            idResults.text = "Input Err"
                            idWorking.text = ""
                            global.reset(listOf("parenthCt", "decimalBool"))
                        }
                    }
                    else{
                        if(idResults.text.isNotEmpty() && idResults.text != "Input Err"){
                            idWorking.text = idResults.text
                            idResults.text = ""
                        }
                    }

                }
                "." ->
                    if(global.getCanPlaceDecimal()){
                        if (idWorking.text.isEmpty() || ((idWorking.text.isNotEmpty()) && idWorking.text.last() != '.')){
                            idWorking.append(view.text)
                            global.setCanPlaceDecimal()
                        }
                    }
                //i can try and create global variables to keep track of the ()'s to see if the opening one has been placed. If it has, then we can set the boolean
                // true that you can place the closing or you can also continue to put more openings. Maybe you can use a counter that can tell you the amount of opening
                // and closing ()'s that have been placed. If they are not the same, then you have an error otherwise you're bing chilling
                "(" -> {
                    global.editOpeningParenthCt("+")
                    idWorking.append(view.text)
                }
                ")" -> {
                    if(global.getOpeningParenthCt() > 0){
                        global.editOpeningParenthCt("-")
                        idWorking.append(view.text)
                    }
                }
                else -> idWorking.append(view.text)
            }
        }
    }

    fun infix(): CharSequence {
        val workView = binding.working
        val operators = listOf(')', '^','+', '-', '/', 'X')
        val numStack = Stack<Float>()
        val opStack = Stack<Char>()
        var numToStack = ""
        var result = ""
        // dont want to actually manipulate the array directly since it'll get messy later
        workView.text.forEachIndexed{index, it ->
            when (it) {
                '(' -> {
                    opStack.push(it)
                }
                in operators -> {
                    Log.i("char", it.toString())
                    numStack.push(numToStack.toFloat())
                    numToStack = ""
                    editStacks(it, numStack, opStack)
                }
                else -> {
                    numToStack += it
                    if(index == workView.text.lastIndex){
                        Log.i("num", numToStack)
                        numStack.push(numToStack.toFloat())
                        numToStack = ""
                    }
                }
            }
        }
        result = finalStackEval(numStack, opStack)
        workView.text=""
        return if(result.last() == '0' && result[result.lastIndex - 1] == '.'){
            result.toFloat().toInt().toString()
        }
        else
            result

    }

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
        global.reset(listOf("parenthCt","decimalBool"))
    }

    // Erase one char from the charSequence in @id/display
    fun delete(view: View) {
        val displayChars = binding.working.text
        if (displayChars.isNotEmpty()) {
            when(displayChars.last()){
                '.' -> {
                    global.reset(listOf("decimalBool"))
                }
                '(' -> {
                    global.editOpeningParenthCt("-")
                }
                ')' ->{
                    global.editOpeningParenthCt("+")
                }
            }
            binding.working.text = displayChars.substring(0, displayChars.length - 1)
        }
    }


}
