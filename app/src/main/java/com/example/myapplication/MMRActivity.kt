package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlin.math.log10
import kotlin.random.Random
import kotlin.math.min
class MMRActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MMRFunction()
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun MMRFunction() {
    val isHeldDown by remember { mutableStateOf(false) } // to see if menu open
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
    var points by remember { mutableIntStateOf(preferencesManager.getMultiplicationPoints()) }

    ///////////////////EmilKode/////////////////////
    var startTime by remember { mutableLongStateOf(System.currentTimeMillis()) } // reset start time
    var positiveStreak by remember { mutableIntStateOf(0) } // reset positive streak
    var negativeStreak by remember { mutableIntStateOf(0) } // reset negative streak
    ///////////////////EmilKode/////////////////////

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

                ///////////////////EmilKode/////////////////////
                val endTime = System.currentTimeMillis() // get current time
                val timeTaken = ((endTime - startTime) / 1000).toInt() // calculate time taken
                ///////////////////EmilKode/////////////////////

                if (answer.toIntOrNull() == correctAnswer) {

                    ///////////////////EmilKode/////////////////////
                    println(timeTaken) // print time taken
                    positiveStreak++ // increment positive streak
                    negativeStreak = 0 // reset negative streak
                    increaseScore(positiveStreak, timeTaken, context)
                    ///////////////////EmilKode/////////////////////

                    result = "Rigtigt!"
                    points++ // increment points
                    preferencesManager.saveMultiplicationPoints(points) // save points
                } else {

                    ///////////////////EmilKode/////////////////////
                    negativeStreak++ // increment negative streak
                    positiveStreak = 0 // reset positive streak
                    decreaseScore(negativeStreak, timeTaken, context) // decrease score
                    ///////////////////EmilKode/////////////////////

                    result = "Forkert! Prøv igen."
                }
                coolDownOn = true //Turns on cooldown for button and text field
                answer = "" // clear the TextField
            }),
            enabled = !coolDownOn // Disables text field when cooldown is on
        )
        Button(
            onClick = {

                ///////////////////EmilKode/////////////////////
                val endTime = System.currentTimeMillis() // get current time
                val timeTaken = ((endTime - startTime) / 1000).toInt() // calculate time taken
                ///////////////////EmilKode/////////////////////

                if (answer.toIntOrNull() == correctAnswer) {

                    ///////////////////EmilKode/////////////////////
                    println(timeTaken) // print time taken
                    positiveStreak++ // increment positive streak
                    negativeStreak = 0 // reset negative streak
                    increaseScore(positiveStreak, timeTaken,context) // increase score
                    ///////////////////EmilKode/////////////////////

                    result = "Rigtigt!"
                    points++ // increment points
                    preferencesManager.saveMultiplicationPoints(points) // save points
                } else {
                    result = "Forkert! Prøv igen."

                    ///////////////////EmilKode/////////////////////
                    negativeStreak++ // increment negative streak
                    positiveStreak = 0 // reset positive streak
                    decreaseScore(negativeStreak, timeTaken, context)
                    ///////////////////EmilKode/////////////////////

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

                ///////////////////EmilKode/////////////////////
                startTime = System.currentTimeMillis() // reset start time
                ///////////////////EmilKode/////////////////////

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

///////////////////EmilKode/////////////////////
private fun increaseScore(streak: Int, time: Int, context: Context) {
    val preferencesManager = PreferencesManager(context)
    var points = preferencesManager.getMMR()

    val baseScore = 50
    val streakMultiplier = (log10((streak + 2).toDouble()) +0.5) // decreases as streak increases
    val timeBonus = if (time <= 5) (5 - time) * 10 else 0 // larger bonus for time less than 5 seconds

    val score = min(((baseScore + timeBonus) * streakMultiplier).toInt(), 150)


    points += score
    preferencesManager.saveMMR(points)
    println(points)
}

private fun decreaseScore(streak: Int, time: Int, context: Context) {
    val preferencesManager = PreferencesManager(context)
    var points = preferencesManager.getMMR()

    val basePenalty = 50
    val streakPenalty = (log10((streak + 2).toDouble()) +0.8) // increases as streak increases
    val timePenalty = if (time <= 5) time * 2 else 10 // smaller penalty for time less than 5 seconds

    val penalty = min(((basePenalty + timePenalty) * streakPenalty).toInt(),100)
    points -= penalty // decrease the score

    if (points < 0) {
        points = 0 // ensure the score doesn't go below zero
    }

    preferencesManager.saveMMR(points) // save the updated score
    println(points)
}
///////////////////EmilKode/////////////////////


@Preview(showBackground = true)
@Composable
private fun MulFunctionPreview() {
    MyApplicationTheme {
        MMRFunction()
    }
}