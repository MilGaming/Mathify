package com.example.myapplication.mathPages

import android.annotation.SuppressLint
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
import com.example.myapplication.scripts.CustomTopBar
import com.example.myapplication.scripts.PreferencesManager
import com.example.myapplication.scripts.StreakBar
import com.example.myapplication.scripts.decreaseScore
import com.example.myapplication.scripts.increaseScore
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.scripts.updateDivQuestion
import kotlinx.coroutines.delay
import kotlin.random.Random

class DivActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DivFunction()
        }
    }
}

data class DivUserStats(val answerTime: Int, val currentMMR: Int, val winningStreak: Int, val index: Int, val activityName: String)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun DivFunction() {
    val context = LocalContext.current
    var answer by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var coolDownOn by remember { mutableStateOf(false) }
    val cooldownTime = 1000L
    val random = Random

    val preferencesManager = PreferencesManager(context)
    var points by remember { mutableIntStateOf(preferencesManager.getDivisionPoints()) }

    var startTime by remember { mutableLongStateOf(System.currentTimeMillis()) } // reset start time
    var positiveStreak by remember { mutableIntStateOf(0) } // reset positive streak
    var negativeStreak by remember { mutableIntStateOf(0) } // reset negative streak
    val mmr = preferencesManager.getDivMMR() // get MMR


    //new shit
    val userStatsList = preferencesManager.getDivStats().toMutableList()

    //Question scalabililty------------------------------------------------------------
    var question by remember {
        mutableStateOf(
            updateDivQuestion(mmr, random)
        )
    }
    val correctAnswer = question.first / question.second

    CustomTopBar()
    StreakBar(positiveStreak) //Add streak score to screen
    Column(
        //Adds padding to button column at the top
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Hvad er ${question.first} ÷ ${question.second}?", fontSize = 24.sp)
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
                val mmr = preferencesManager.getDivMMR()
                ///////////////////EmilKode/////////////////////

                if (answer.toIntOrNull() == correctAnswer) {

                    ///////////////////EmilKode/////////////////////
                    println(timeTaken) // print time taken
                    positiveStreak++ // increment positive streak
                    negativeStreak = 0 // reset negative streak
                    ///////////////////EmilKode/////////////////////

                    result = "Rigtigt!"
                    points++ // increment points
                    preferencesManager.saveDivisionPoints(points) // save points

                    preferencesManager.saveDivMMR(increaseScore(positiveStreak, timeTaken, mmr, points))
                } else {

                    ///////////////////EmilKode/////////////////////
                    negativeStreak++ // increment negative streak
                    positiveStreak = 0 // reset positive streak
                    preferencesManager.saveDivMMR(decreaseScore(negativeStreak, timeTaken, mmr, points)) // decrease score
                    ///////////////////EmilKode/////////////////////

                    result = "Forkert! Prøv igen."
                }

                coolDownOn = true //Turns on cooldown for button and text field
                answer = "" // clear the TextField

                // Create a new UserStats object and add it to the list
                val index = userStatsList.size // Get the current size of the list
                val activityName = "DivActivity" // Name of the current activity
                val userStats = DivUserStats(timeTaken, mmr, positiveStreak, index, activityName) // Create a new AddUserStats object with the index and activity name
                userStatsList.add(userStats) // Add the new object to the list
                preferencesManager.saveDivStats(userStatsList) // Save the list

            }),
            enabled = !coolDownOn // Disables text field when cooldown is on
        )
        Button(
            onClick = {

                ///////////////////EmilKode/////////////////////
                val endTime = System.currentTimeMillis() // get current time
                val timeTaken = ((endTime - startTime) / 1000).toInt() // calculate time taken
                val mmr = preferencesManager.getDivMMR() // get MMR
                ///////////////////EmilKode/////////////////////

                if (answer.toIntOrNull() == correctAnswer) {

                    ///////////////////EmilKode/////////////////////
                    println(timeTaken) // print time taken
                    positiveStreak++ // increment positive streak
                    negativeStreak = 0 // reset negative streak

                    ///////////////////EmilKode/////////////////////

                    result = "Rigtigt!"
                    points++ // increment points
                    preferencesManager.saveDivisionPoints(points) // save points

                    preferencesManager.saveDivMMR(increaseScore(positiveStreak, timeTaken, mmr, points))
                } else {
                    result = "Forkert! Prøv igen."

                    ///////////////////EmilKode/////////////////////
                    negativeStreak++ // increment negative streak
                    positiveStreak = 0 // reset positive streak
                    preferencesManager.saveDivMMR(decreaseScore(negativeStreak, timeTaken, mmr, points))
                    ///////////////////EmilKode/////////////////////

                }
                coolDownOn = true //Turns on cooldown for button and text field
                answer = "" // clear the TextField

                // Create a new UserStats object and add it to the list
                val index = userStatsList.size // Get the current size of the list
                val activityName = "DivActivity" // Name of the current activity
                val userStats = DivUserStats(timeTaken, mmr, positiveStreak, index, activityName) // Create a new AddUserStats object with the index and activity name
                userStatsList.add(userStats) // Add the new object to the list
                preferencesManager.saveDivStats(userStatsList) // Save the list
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
                //divisor = random.nextInt(7) + 1 // Avoid zero
                //multiplier = random.nextInt(10)
                //dividend = divisor * multiplier // update the question

                ///////////////////EmilKode/////////////////////
                startTime = System.currentTimeMillis() // reset start time
                ///////////////////EmilKode/////////////////////

                //Question scalabililty------------------------------------------------------------
                question = updateDivQuestion(mmr, random) // update the question according to MMR

                coolDownOn = false // Turns off cooldown for button
            }
        }
    }
    // Display the score in the top right corner
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Text(
            text = "Points: $mmr",
            modifier = Modifier
                .padding(top = 16.dp, end = 16.dp)
                .align(Alignment.TopEnd),
            fontSize = 24.sp
        )
    }
}



@Preview(showBackground = true)
@Composable
private fun DivFunctionPreview() {
    MyApplicationTheme {
        DivFunction()
    }
}