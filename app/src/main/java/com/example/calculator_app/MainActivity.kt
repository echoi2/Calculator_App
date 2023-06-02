package com.example.calculator_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils.substring
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.text.isDigitsOnly
import com.example.calculator_app.ui.theme.Calculator_AppTheme
import com.example.calculator_app.databinding.CalculatorBinding


/*
RULES:

1. Display can't start with an operation sign unless it is a minus sign
2. Display can't have multiple operation signs next to each other
3. Minus is the only operation sign that can come after another operation sign other than itself
3. Display can have operation signs right after decimal point
4. If the char before a minus sign is another operation sign and if you click another opertion
   sign after that minus sign, then you just delete that minus sign
 */



class MainActivity : ComponentActivity() {
    private lateinit var binding: CalculatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CalculatorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
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
