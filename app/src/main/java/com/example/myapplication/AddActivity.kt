package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class AddActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AddFunction()
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun AddFunction() {
    var isHeldDown = remember { mutableStateOf(false) } // to see if menu open
    val openDialog = remember { mutableStateOf(false) } // for popup
    val context = LocalContext.current
    var answer by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var coolDownOn by remember { mutableStateOf(false) }
    val cooldownTime = 1000L
    val random = Random
    var question by remember { mutableStateOf(Pair(random.nextInt(10), random.nextInt(10))) }
    val correctAnswer = question.first + question.second

    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray),
                title = { Text("Mathify") },
                actions = {
                    IconButton(onClick = { //home button
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Icon(Icons.Filled.Home, contentDescription = "Home")
                    }
                    Surface(
                        color = if (isHeldDown.value) Color.Gray else Color.Transparent,

                        shape = CircleShape
                    ) {
                        IconButton(
                            onClick = { //aceivment pop up menu
                                if (!openDialog.value) { // if the popup is open
                                    openDialog.value = !openDialog.value
                                    isHeldDown.value = !isHeldDown.value

                                }

                            },
                            enabled = !isHeldDown.value

                            ) {
                            Icon(Icons.Filled.Star, contentDescription = "Trophy")
                        }
                    }
                }
            )
        },
        content = {
            Column(
                //Adds padding to button column at the top
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 100.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            }
            Spacer(modifier = Modifier.height(50.dp))
        })



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
                } else {
                    result = "Forkert! Prøv igen."
                }
                coolDownOn = true //Turns on cooldown for button and text field
            }),
            enabled = !coolDownOn // Disables text field when cooldown is on
        )
        Button(
            onClick = {
                if (answer.toIntOrNull() == correctAnswer) {
                    result = "Rigtigt!"
                } else {
                    result = "Forkert! Prøv igen."
                }
                coolDownOn = true //Turns on cooldown for button and text field
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
    if (openDialog.value) { //Opens the popup
        Popup(openDialog, isHeldDown)
        val keyboardController = LocalSoftwareKeyboardController.current
        keyboardController?.hide()
    }
}

@Composable
fun Popup(openDialog: MutableState<Boolean>, isHeldDown: MutableState<Boolean>){

    val cornerSize = 10.dp //box specs
    val coroutineScope = rememberCoroutineScope()

        Popup(
            alignment = Alignment.Center, //here we mention the pos
            properties = PopupProperties(), //popup properties
            onDismissRequest = {
                    coroutineScope.launch {
                        delay(200) // delay for 200 miliseconds
                        openDialog.value = false
                        isHeldDown.value = false
                    }

            }
        )
        {

            Column {

                    Box( //the box content

                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.8f)
                            .padding(top = 2.dp)
                            .background(
                                Color.Green,
                                RoundedCornerShape(cornerSize)
                            )
                            .border(
                                1.dp,
                                Color.Black,
                                RoundedCornerShape(cornerSize)
                            )

                    ) {

                        Column(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp)

                        ) {
                            Text(
                                text = "Achivments1",
                                modifier = Modifier.padding(vertical = 5.dp),
                                fontSize = 16.sp
                            )

                            Divider(color = Color.Black, thickness = 1.dp)

                            Text(
                                text = "Achivments2",
                                modifier = Modifier.padding(vertical = 5.dp),
                                fontSize = 16.sp
                            )

                            Divider(color = Color.Black, thickness = 1.dp)

                            Text(
                                text = "Achivments3",
                                modifier = Modifier.padding(vertical = 5.dp),
                                fontSize = 16.sp
                            )

                            Divider(color = Color.Black, thickness = 1.dp)

                        }


                    }

                }
            }
        }






@Preview(showBackground = true)
@Composable
private fun AddFunctionPreview() {
    MyApplicationTheme {
        AddFunction()
    }
}