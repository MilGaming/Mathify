package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    Scaffold(
            topBar = {
                TopAppBar(
                        title = { Text("Hello") },
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
                    Button(onClick = { /* Handle button 1 click */ }) {
                        Text("Button 1")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { /* Handle button 2 click */ }) {
                        Text("Button 2")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { /* Handle button 3 click */ }) {
                        Text("Button 3")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { /* Handle button 4 click */ }) {
                        Text("Button 4")
                    }
                }
            }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}