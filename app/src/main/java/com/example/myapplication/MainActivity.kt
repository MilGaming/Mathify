package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.Purple40

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
            }
        }
    }

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@ExperimentalAnimationApi
@Composable
fun MyApp() {
    var isExpanded = rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
            topBar = {
                TopAppBar(colors = topAppBarColors(containerColor = Color.LightGray),
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
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { /* Handle button 1 click */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White),
                        modifier = Modifier
                            .wrapContentSize(Alignment.Center)
                            .fillMaxWidth(0.8f)) {
                        Text("+", fontSize = 50.sp, textAlign = TextAlign.Center)
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    //Subtraction button with expanding button
                    Box(modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .background(Purple40)){
                        Button(onClick = { /* Handle button 2 click */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White),
                        modifier = Modifier
                            .wrapContentSize(Alignment.CenterStart)
                            .fillMaxWidth(0.8f)) {
                        Text("-", fontSize = 50.sp, textAlign = TextAlign.Center)
                        }
                        Button(onClick = {isExpanded.value = !isExpanded.value},
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .fillMaxWidth(0.2f)) {
                            Text("+", fontSize = 25.sp, textAlign = TextAlign.Center)
                        }
                    }

                    Spacer(modifier = Modifier.height(50.dp))
                    Button(onClick = { /* Handle button 3 click */ },
                        modifier = Modifier
                            .wrapContentSize(Alignment.Center)
                            .fillMaxWidth(0.8f)) {
                        Text("×", fontSize = 50.sp, textAlign = TextAlign.Center)
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                    Button(onClick = { /* Handle button 4 click */ },
                        modifier = Modifier
                            .wrapContentSize(Alignment.Center)
                            .fillMaxWidth(0.8f)) {
                        Text("÷", fontSize = 50.sp, textAlign = TextAlign.Center)
                    }
                }
            }
    )
}


@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}

@ExperimentalAnimationApi
@Composable
fun Expandable() {

    var isExpanded = rememberSaveable {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier.clickable {
            isExpanded.value = !isExpanded.value
        }
    ) {
        Column() {

            Text(text = "This text is always shown",
                modifier = Modifier.padding(4.dp))

            AnimatedVisibility(visible = isExpanded.value) {
                Row() {
                    TextField(label = { Text("Amount")} ,value = "", onValueChange = {})
                    Button(onClick = {
                        isExpanded.value = !isExpanded.value
                    }) {

                    }
                }
            }

        }

    }

}