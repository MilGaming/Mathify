package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.myapplication.ui.theme.Purple40
import com.example.myapplication.ui.theme.Purple80
import com.example.myapplication.ui.theme.PurpleGrey80
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CustomTopBar() {
    val isHeldDown = remember { mutableStateOf(false) } // to see if menu open
    val openDialog = remember { mutableStateOf(false) } // for popup
    val showInfo = remember { mutableStateOf(false) } // for Info dialog
    val context = LocalContext.current
    val currentActivity = context as Activity
    val currentActivityName = currentActivity.localClassName

    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray),
                title = { Text("MathTech") },
                actions = {
                    //Home button is not shown in the main activity
                    if (currentActivityName != "MainActivity") {
                        IconButton(onClick = { //home button
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                        }) {
                            Icon(Icons.Filled.Home, contentDescription = "Home")
                        }
                    }
                    else{
                        IconButton(onClick = { //home button
                            showInfo.value = !showInfo.value
                        }) {
                            Icon(Icons.Filled.Info, contentDescription = "Info")
                        }
                    }
                    Surface(
                        color = if (isHeldDown.value) Color.Gray else Color.Transparent,

                        shape = CircleShape
                    ) {
                        IconButton(
                            onClick = { //achievement pop up menu
                                isHeldDown.value = !isHeldDown.value
                                openDialog.value = !openDialog.value
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
    if (openDialog.value) {
        AchievementPopup(openDialog, isHeldDown)
        val keyboardController = LocalSoftwareKeyboardController.current
        keyboardController?.hide()
    }
    if (showInfo.value) {
        Info(onDismiss = { showInfo.value = false })
    }
}

@Composable
fun AchievementPopup(openDialog: MutableState<Boolean>,isHeldDown: MutableState<Boolean>){
    val cornerSize = 10.dp //box specs
    val coroutineScope = rememberCoroutineScope()
    data class Achievement(val name: String, val currentProgress: Int, val goalProgress: Int)
    val achievements = remember {mutableStateListOf(
        Achievement("Præstation 1", 50, 100),
        Achievement("Præstation 2", 70, 200),
        Achievement("Præstation 3", 30, 150)
    ) }

    androidx.compose.ui.window.Popup(
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
                        Purple80,
                        RoundedCornerShape(cornerSize)
                    )
                    .border(
                        5.dp,
                        Purple40,
                        RoundedCornerShape(cornerSize)
                    )
            ) {

                Column(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)

                ) {
                    Text(
                        text = "Præstationer",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                    // Loop through the achievements and display them
                    for (achievement in achievements) {
                        Column {
                            Text(
                                text = achievement.name,
                                modifier = Modifier.padding(vertical = 5.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                                )
                            Box(
                                contentAlignment = Alignment.Center,
                            ) {
                                LinearProgressIndicator(
                                    progress = achievement.currentProgress.toFloat() / achievement.goalProgress, // Calculate the progress
                                    color = Purple40, // Customize the color of the progress bar
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(20.dp)
                                        .clip(RoundedCornerShape(5.dp)) // Apply rounded corners

                                )
                                Text(
                                    text = "${achievement.currentProgress}/${achievement.goalProgress}",
                                    modifier = Modifier
                                        .padding(vertical = 5.dp)
                                        .wrapContentHeight(align = Alignment.CenterVertically),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                )
                                //Text contour
                                Text(
                                    text = "${achievement.currentProgress}/${achievement.goalProgress}",
                                    modifier = Modifier
                                        .padding(vertical = 5.dp)
                                        .wrapContentHeight(align = Alignment.CenterVertically),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    style = TextStyle.Default.copy(
                                        fontSize = 18.sp,
                                        drawStyle = Stroke(
                                            miter = 50f,
                                            width = 3f,
                                            join = StrokeJoin.Round
                                        )
                                    )
                                )
                            }
                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 5.dp)
                                )
                        }
                    }
                    Button(onClick = {
                        achievements.add(Achievement("New Achievement", 0, 100))
                    }) {
                        Text("Tilføj Præstation")
                    }
                }
            }
        }
    }
}



@Composable
fun Info(onDismiss: () -> Unit){
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