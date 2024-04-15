package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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

//Shared preferences for points in the different activities
class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyApp", Context.MODE_PRIVATE)


    // Addition points
    fun saveAdditionPoints(points: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("ADDITION_POINTS", points)
        editor.apply()
    }

    fun getAdditionPoints(): Int {
        return sharedPreferences.getInt("ADDITION_POINTS", 0)
    }

    // Subtraction points
    fun saveSubtractionPoints(points: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("SUBTRACTION_POINTS", points)
        editor.apply()
    }

    fun getSubtractionPoints(): Int {
        return sharedPreferences.getInt("SUBTRACTION_POINTS", 0)
    }

    // Multiplication points
    fun saveMultiplicationPoints(points: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("MULTIPLICATION_POINTS", points)
        editor.apply()
    }

    fun getMultiplicationPoints(): Int {
        return sharedPreferences.getInt("MULTIPLICATION_POINTS", 0)
    }

    // Division points
    fun saveDivisionPoints(points: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("DIVISION_POINTS", points)
        editor.apply()
    }

    fun getDivisionPoints(): Int {
        return sharedPreferences.getInt("DIVISION_POINTS", 0)
    }

    //emilkode//
    fun saveAddMMR(mmr: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("AddMMR", mmr)
        editor.apply()
    }

    fun getAddMMR(): Int {
        return sharedPreferences.getInt("AddMMR", 0)
    }
    fun saveMulMMR(mmr: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("MulMMR", mmr)
        editor.apply()
    }

    fun getMulMMR(): Int {
        return sharedPreferences.getInt("MulMMR", 0)
    }
    fun saveSubMMR(mmr: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("SubMMR", mmr)
        editor.apply()
    }

    fun getSubMMR(): Int {
        return sharedPreferences.getInt("SubMMR", 0)
    }
    fun saveDivMMR(mmr: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("DivMMR", mmr)
        editor.apply()
    }
    fun getDivMMR(): Int {
        return sharedPreferences.getInt("DivMMR", 0)
    }
    //emilkode//

    //andersKode//
    fun saveFirstLogin(firstLogin: Boolean) {
        val editor=sharedPreferences.edit()
        editor.putBoolean("FIRST_LOGIN", firstLogin)
        editor.apply()
    }

    fun getFirstLogin(): Boolean {
        return sharedPreferences.getBoolean("FIRST_LOGIN", true)
    }
    //andersKode//
    fun clearPreferences() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun MyApp() {
    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)
    var isFirstLogin by remember { mutableStateOf(preferencesManager.getFirstLogin()) }
    //preferencesManager.clearPreferences()
    if (isFirstLogin) {
        WelcomePopup(onDismiss = {
            isFirstLogin = !isFirstLogin
            preferencesManager.saveFirstLogin(false)
        })
    } else {
        //Toggle variables for all 4 expand buttons
        val isExpandedB1 = rememberSaveable {
            mutableStateOf(false)
        }
        val isExpandedB2 = rememberSaveable {
            mutableStateOf(false)
        }
        val isExpandedB3 = rememberSaveable {
            mutableStateOf(false)
        }
        val isExpandedB4 = rememberSaveable {
            mutableStateOf(false)
        }
        //Saving the current activity context
        val context = LocalContext.current
        val preferencesManager = PreferencesManager(context)
        var totalScore by remember { mutableIntStateOf(0) }
        // Animate the rotation
        val rotationB1 by animateFloatAsState(if (isExpandedB1.value) 45f else 0f, label = "")
        val rotationB2 by animateFloatAsState(if (isExpandedB2.value) 45f else 0f, label = "")
        val rotationB3 by animateFloatAsState(if (isExpandedB3.value) 45f else 0f, label = "")
        val rotationB4 by animateFloatAsState(if (isExpandedB4.value) 45f else 0f, label = "")

        // Update the total score whenever needed
        totalScore = preferencesManager.getAdditionPoints() +
                preferencesManager.getSubtractionPoints() +
                preferencesManager.getMultiplicationPoints() +
                preferencesManager.getDivisionPoints()

        CustomTopBar()
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
            ) {

            }

            Column(
                //Adds padding to button column at the top
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text( //Mads added moved text ------------------------------------------------------------------------------------
                    text = "Vælg en udfordring ved at trykke på den og regn løs!",
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
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
                                .fillMaxWidth(0.9f)
                        ) {
                            Text("+", fontSize = 50.sp, textAlign = TextAlign.Center)
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .fillMaxWidth(0.1f),
                        ) {
                            Button(
                                onClick = { isExpandedB1.value = !isExpandedB1.value },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Purple80,
                                    contentColor = Purple40
                                ),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(30.dp),
                                shape = CircleShape
                            ) {
                            }
                            Text(
                                "＋",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .rotate(rotationB1),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Purple40,
                            )
                        }
                    }
                    //Whatever needs to be under button 1 to has to be added below here--------
                    this.AnimatedVisibility(visible = isExpandedB1.value) {
                        Text(
                            text = "Addition Statistics will be shown here!!!" +
                                    "\nPoints: ${preferencesManager.getAdditionPoints()}" +
                                    "\nMMR: ${preferencesManager.getAddMMR()}",
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
                                .fillMaxWidth(0.9f)
                        ) {
                            Text("−", fontSize = 50.sp, textAlign = TextAlign.Center)
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .fillMaxWidth(0.1f),
                        ) {
                            Button(
                                onClick = { isExpandedB2.value = !isExpandedB2.value },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Purple80,
                                    contentColor = Purple40
                                ),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(30.dp),
                                shape = CircleShape
                            ) {
                            }
                            Text(
                                "＋",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .rotate(rotationB2),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Purple40,
                            )
                        }
                    }
                    //Whatever needs to be under button 2 to has to be added below here--------
                    this.AnimatedVisibility(visible = isExpandedB2.value) {
                        Text(
                            text = "Subtraction Statistics will be shown here!!!" +
                                    "\nPoints: ${preferencesManager.getSubtractionPoints()}" +
                                    "\nMMR: ${preferencesManager.getSubMMR()}",
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
                                .fillMaxWidth(0.9f)
                        ) {
                            Text("×", fontSize = 50.sp, textAlign = TextAlign.Center)
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .fillMaxWidth(0.1f),
                        ) {
                            Button(
                                onClick = { isExpandedB3.value = !isExpandedB3.value },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Purple80,
                                    contentColor = Purple40
                                ),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(30.dp),
                                shape = CircleShape
                            ) {
                            }
                            Text(
                                "＋",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .rotate(rotationB3),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Purple40,
                            )
                        }
                    }
                    //Whatever needs to be under button 3 to has to be added below here--------
                    this.AnimatedVisibility(visible = isExpandedB3.value) {
                        Text(
                            text = "Multiplication Statistics will be shown here!!!" +
                                    "\nPoints: ${preferencesManager.getMultiplicationPoints()}" +
                                    "\nMMR: ${preferencesManager.getMulMMR()}",
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
                                .fillMaxWidth(0.9f)
                        ) {
                            Text("÷", fontSize = 50.sp, textAlign = TextAlign.Center)
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .fillMaxWidth(0.1f),
                        ) {
                            Button(
                                onClick = { isExpandedB4.value = !isExpandedB4.value },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Purple80,
                                    contentColor = Purple40
                                ),
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(30.dp),
                                shape = CircleShape
                            ) {
                            }
                            Text(
                                "＋",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .rotate(rotationB4),
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                color = Purple40,
                            )
                        }
                    }
                    //Whatever needs to be under button 4 to has to be added below here--------
                    this.AnimatedVisibility(visible = isExpandedB4.value) {
                        Text(
                            text = "Division Statistics will be shown here!!!" +
                                    "\nPoints: ${preferencesManager.getDivisionPoints()}" +
                                    "\nMMR: ${preferencesManager.getDivMMR()}",
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(0.8f),
                            fontSize = 5.em
                        )
                    }
                }
            }

        }
        // Display the total score in the top right corner
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Text(
                text = "Samlede points: $totalScore",
                modifier = Modifier
                    .padding(top = 16.dp, end = 16.dp)
                    .align(Alignment.TopEnd),
                fontSize = 24.sp
            )
        }
    }
}

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