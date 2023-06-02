package com.example.calculator_app

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

    fun operationAction(view: View) {

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
