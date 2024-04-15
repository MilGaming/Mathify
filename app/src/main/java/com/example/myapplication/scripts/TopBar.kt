package com.example.myapplication.scripts

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.myapplication.MainActivity
import com.example.myapplication.ui.theme.Purple40
import com.example.myapplication.ui.theme.Purple80
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

//data class Achievement(val index: Int, val name: String, val currentProgress: Int, val goalProgress: Int)


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