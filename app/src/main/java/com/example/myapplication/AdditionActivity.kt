package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.random.Random

class AdditionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    Addition()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Addition() {
    var answer by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val random = Random
    val number1 = random.nextInt(10)
    val number2 = random.nextInt(10)
    val correctAnswer = number1 + number2
    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray),
                title = { Text("Mathify") },
                actions = {
                    IconButton(onClick = { /* Handle home button click */ }) {
                        Icon(Icons.Filled.Home, contentDescription = "Home")
                    }
                    IconButton(onClick = { /* Handle trophy button click */ }) {
                        Icon(Icons.Filled.Star, contentDescription = "Trophy")
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
                Text(text = "Hvad er $number1 + $number2?", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = answer,
                    onValueChange = { answer = it },
                    label = { Text("Skriv svar her") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Button(
                    onClick = {
                        if (answer.toIntOrNull() == correctAnswer) {
                            result = "Rigtigt!"
                        } else {
                            result = "Forkert! Prøv igen."
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Tjek")
                }
                Text(text = result, fontSize = 24.sp, modifier = Modifier.padding(top = 16.dp))
            }
            //Create a text field i which the user can insert the answers for the addition equation that will be given above in text. There will be a button to submit the answer. And a response if the answer was correct or false
        }
    )
}
