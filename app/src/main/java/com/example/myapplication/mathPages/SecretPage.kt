package com.example.myapplication.mathPages

import android.os.Bundle
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import com.example.myapplication.scripts.CustomTopBar
import com.example.myapplication.scripts.PreferencesManager
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
class SecretPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                }
                SaveJsonButton()
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SaveJsonButton() {
    CustomTopBar()
    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)
    val addStatsList = preferencesManager.getAddStats()
    val subStatsList = preferencesManager.getSubStats()
    val mulStatsList = preferencesManager.getMulStats()
    val divStatsList = preferencesManager.getDivStats()

    // Combine all the lists into a single list
    val allStatsList = listOf(addStatsList, subStatsList, mulStatsList, divStatsList)

    val gson = Gson()
    val userStatsJson = gson.toJson(allStatsList)

    val coroutineScope = rememberCoroutineScope()
    var isDeleteButtonEnabled by remember { mutableStateOf(true) }
    var isButtonEnabled by remember { mutableStateOf(true) }


    // State for the file name input
    var fileName by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.weight(1f)) // This will push the TextField and "Save JSON" button to the middle

            val keyboardController = LocalSoftwareKeyboardController.current

            TextField(
                value = fileName,
                onValueChange = { fileName = it },
                label = { Text("Enter file name") },
                keyboardActions = KeyboardActions(onDone = {
                    if (fileName.isNotEmpty()) {
                        coroutineScope.launch {
                            // Disable the button
                            isButtonEnabled = false

                            // Save the JSON
                            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            val file = File(downloadsDir, "$fileName.json")
                            file.writeText(userStatsJson)
                            fileName = "" // Clear the text field
                            preferencesManager.clearPreferences() // Clear preferences after saving the file

                            // Hide the keyboard
                            keyboardController?.hide()

                            // Wait for 3 seconds
                            delay(3000L)

                            // Enable the button
                            isButtonEnabled = true
                        }
                    }
                }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Text)
            )

            Button(
                onClick = {
                    // Check if the fileName is not empty
                    if (fileName.isNotEmpty()) {
                        coroutineScope.launch {
                            // Disable the button
                            isButtonEnabled = false

                            // Save the JSON
                            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            val file = File(downloadsDir, "$fileName.json")
                            file.writeText(userStatsJson)
                            fileName = "" // Clear the text field
                            preferencesManager.clearPreferences() // Clear preferences after saving the file

                            // Hide the keyboard
                            keyboardController?.hide()

                            // Wait for 3 seconds
                            delay(3000L)

                            // Enable the button
                            isButtonEnabled = true
                        }
                    }
                },
                enabled = isButtonEnabled
            ) {
                Text("Save JSON")
            }

            Spacer(modifier = Modifier.weight(2f)) // This will push the "remove all data" button slightly up from the bottom

            Button(
                onClick = {
                    coroutineScope.launch {
                        // Disable the button
                        isDeleteButtonEnabled = false

                        // Clear preferences
                        preferencesManager.clearPreferences()

                        // Wait for 3 seconds
                        delay(3000L)

                        // Enable the button
                        isDeleteButtonEnabled = true
                    }
                },
                enabled = isDeleteButtonEnabled,
                colors = ButtonDefaults.textButtonColors(
                    Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text("remove all data")
            }

            Spacer(modifier = Modifier.weight(0.5f)) // This will add a small space below the "remove all data" button
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        SaveJsonButton()
    }
}