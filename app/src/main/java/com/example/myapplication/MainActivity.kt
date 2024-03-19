package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Purple40
import com.example.myapplication.ui.theme.Purple80

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
            }
        }
    }

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()

}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@ExperimentalAnimationApi
@Composable
fun MyApp() {
    var welcomeShown by remember { mutableStateOf(true) }

    if (welcomeShown) {
        WelcomePopup(onDismiss = { welcomeShown = false })
    } else

    {
        //Toggle variables for all 4 expand buttons
        var isExpandedB1 = rememberSaveable {
            mutableStateOf(false)
        }
        var isExpandedB2 = rememberSaveable {
            mutableStateOf(false)
        }
        var isExpandedB3 = rememberSaveable {
            mutableStateOf(false)
        }
        var isExpandedB4 = rememberSaveable {
            mutableStateOf(false)
        }


        //Saving the current activity context
        val context = LocalContext.current
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
                    //Adds padding to button column at the top
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //Button 1 with expanding button
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                        ) {
                            Button(
                                onClick = {
                                    val intent = Intent(context, AddActivity::class.java)
                                    context.startActivity(intent)
                                },
                                modifier = Modifier
                                    .wrapContentSize(Alignment.CenterStart)
                                    .fillMaxWidth(0.8f)
                            ) {
                                Text("+", fontSize = 50.sp, textAlign = TextAlign.Center)
                            }
                            Button(
                                onClick = { isExpandedB1.value = !isExpandedB1.value },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Purple80,
                                    contentColor = Purple40
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .fillMaxWidth(0.2f)
                            ) {
                                Text("+", fontSize = 25.sp, textAlign = TextAlign.Center)
                            }
                        }
                        //Whatever needs to be under button 1 to has to be added below here--------
                        this@Column.AnimatedVisibility(visible = isExpandedB1.value) {
                            Text(
                                text = "Addition Statistics will be shown here!!!",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxWidth(0.8f),
                                fontSize = 5.em
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    //Button 2 with expanding button
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                        ) {
                            Button(
                                onClick = {
                                    val intent = Intent(context, SubActivity::class.java)
                                    context.startActivity(intent)
                                },
                                modifier = Modifier
                                    .wrapContentSize(Alignment.CenterStart)
                                    .fillMaxWidth(0.8f)
                            ) {
                                Text("-", fontSize = 50.sp, textAlign = TextAlign.Center)
                            }
                            Button(
                                onClick = { isExpandedB2.value = !isExpandedB2.value },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Purple80,
                                    contentColor = Purple40
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .fillMaxWidth(0.2f)
                            ) {
                                Text("+", fontSize = 25.sp, textAlign = TextAlign.Center)
                            }
                        }
                        //Whatever needs to be under button 2 to has to be added below here--------
                        this@Column.AnimatedVisibility(visible = isExpandedB2.value) {
                            Text(
                                text = "Subtraction Statistics will be shown here!!!",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxWidth(0.8f),
                                fontSize = 5.em
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(50.dp))

                    //Button 3 with expanding button
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                        ) {
                            Button(
                                onClick = {
                                    val intent = Intent(context, MulActivity::class.java)
                                    context.startActivity(intent)
                                },
                                modifier = Modifier
                                    .wrapContentSize(Alignment.CenterStart)
                                    .fillMaxWidth(0.8f)
                            ) {
                                Text("×", fontSize = 50.sp, textAlign = TextAlign.Center)
                            }
                            Button(
                                onClick = { isExpandedB3.value = !isExpandedB3.value },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Purple80,
                                    contentColor = Purple40
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .fillMaxWidth(0.2f)
                            ) {
                                Text("+", fontSize = 25.sp, textAlign = TextAlign.Center)
                            }
                        }
                        //Whatever needs to be under button 3 to has to be added below here--------
                        this@Column.AnimatedVisibility(visible = isExpandedB3.value) {
                            Text(
                                text = "Multiplication Statistics will be shown here!!!",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxWidth(0.8f),
                                fontSize = 5.em
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(50.dp))

                    //Button 4 with expanding button
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                        ) {
                            Button(
                                onClick = {
                                    val intent = Intent(context, DivActivity::class.java)
                                    context.startActivity(intent)
                                },
                                modifier = Modifier
                                    .wrapContentSize(Alignment.CenterStart)
                                    .fillMaxWidth(0.8f)
                            ) {
                                Text("÷", fontSize = 50.sp, textAlign = TextAlign.Center)
                            }
                            Button(
                                onClick = { isExpandedB4.value = !isExpandedB4.value },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Purple80,
                                    contentColor = Purple40
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .fillMaxWidth(0.2f)
                            ) {
                                Text("+", fontSize = 25.sp, textAlign = TextAlign.Center)
                            }
                        }
                        //Whatever needs to be under button 4 to has to be added below here--------
                        this@Column.AnimatedVisibility(visible = isExpandedB4.value) {
                            Text(
                                text = "Division Statistics will be shown here!!!",
                                modifier = Modifier
                                    .padding(4.dp)
                                    .fillMaxWidth(0.8f),
                                fontSize = 5.em
                            )
                        }
                    }
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomePopup(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Velkommen til Mathify") },
        text = {
            Column {
                Text("Denne app vil hjælpe dig med at øve dig på matematik!")
                Spacer(modifier = Modifier.height(8.dp))
                Text("så vælg en udfordring ved at trykke på den og regn løs!")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Forstået")
            }
        }
    )
}
