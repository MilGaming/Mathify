package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

class MulActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MulFunction()
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MulFunction() {
    var isHeldDown by remember { mutableStateOf(false) } // to see if menu open
    val openDialog = remember { mutableStateOf(false) } // for popup
    val context = LocalContext.current
    var answer by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var coolDownOn by remember { mutableStateOf(false) }
    val cooldownTime = 1000L
    val random = Random
    var question by remember { mutableStateOf(Pair(random.nextInt(10), random.nextInt(10))) }
    val correctAnswer = question.first + question.second
    val preferencesManager = PreferencesManager(context)
    var points by remember { mutableStateOf(preferencesManager.getMultiplicationPoints()) }

    CustomTopBar(isHeldDown, openDialog.value, "Mathify")
    Column(
        //Adds padding to button column at the top
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hvad er ${question.first} + ${question.second}?", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = answer,
            onValueChange = { answer = it },
            label = { Text("Skriv svar her") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if (answer.toIntOrNull() == correctAnswer) {
                    result = "Rigtigt!"
                    points++ // increment points
                    preferencesManager.saveMultiplicationPoints(points) // save points
                } else {
                    result = "Forkert! Prøv igen."
                }
                coolDownOn = true //Turns on cooldown for button and text field
                answer = "" // clear the TextField
            }),
            enabled = !coolDownOn // Disables text field when cooldown is on
        )
        Button(
            onClick = {
                if (answer.toIntOrNull() == correctAnswer) {
                    result = "Rigtigt!"
                    points++ // increment points
                    preferencesManager.saveMultiplicationPoints(points) // save points
                } else {
                    result = "Forkert! Prøv igen."
                }
                coolDownOn = true //Turns on cooldown for button and text field
                answer = "" // clear the TextField
            },
            enabled = !coolDownOn,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Tjek")
        }
        Text(text = result, fontSize = 24.sp, modifier = Modifier.padding(top = 16.dp))
        if (coolDownOn) {
            Text(text = "Vent venligst...", fontSize = 24.sp, modifier = Modifier.padding(top = 16.dp))
            // Coroutine to update the question after 3 seconds
            LaunchedEffect(key1 = coolDownOn) {
                delay(cooldownTime) // delay for 3 seconds
                question = Pair(random.nextInt(10), random.nextInt(10)) // update the question
                coolDownOn = false // Turns off cooldown for button
            }
        }
    }
    // Display the score in the top right corner
    Box(
        modifier = Modifier.fillMaxSize().padding(top = 50.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Text(
            text = "Points: $points",
            modifier = Modifier.padding(top = 16.dp, end = 16.dp).align(Alignment.TopEnd),
            fontSize = 24.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun MulFunctionPreview() {
    MyApplicationTheme {
        MulFunction()
    }
}