package com.example.calculator_app

import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class MainActivityTest{

    @get:Rule
    val rule = createComposeRule()
    val buttonDel = hasText("⌫") and hasClickAction()

    @Test
    fun clickDelete_deletesChar(){
        rule.setContent { MainActivity() }

        rule.onNode(buttonDel).performClick()


    }
}