package com.example.calculator_app

import android.os.Bundle
import android.text.TextUtils.substring
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

    }

    fun operationAction(view: View) {

    }
    // Clear the data in the results and working TextViews
    fun clear(view: View){
        binding.results.text = ""
        binding.working.text = ""
    }
}
