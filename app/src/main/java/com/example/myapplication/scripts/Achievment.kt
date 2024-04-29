package com.example.myapplication.scripts

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.myapplication.ui.theme.Purple40
import com.example.myapplication.ui.theme.Purple80
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class Achievement(
    val index: Int,
    val name: String,
    val currentProgress: Int,
    val goalProgress: Int
)

@Composable
fun AchievementPopup(openDialog: MutableState<Boolean>, isHeldDown: MutableState<Boolean>) {
    val cornerSize = 10.dp //box specs
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val sharedPreferencesManager = PreferencesManager(context)
    val achievements =
        remember { mutableStateListOf(*sharedPreferencesManager.getAchievements().toTypedArray()) }
    val achievementIndex =
        remember { mutableIntStateOf(sharedPreferencesManager.getAchievementIndex()) }
    val showAchievement1 = remember { mutableStateOf(true) }
    val showAchievement2 = remember { mutableStateOf(true) }
    val showAchievement3 = remember { mutableStateOf(true) }
    val showAchievement4 = remember { mutableStateOf(true) }
    val showAchievement5 = remember { mutableStateOf(true) }


    //Achievement values
    var achievement1Value by remember { mutableStateOf(sharedPreferencesManager.getFirstLogin()) }
    var achievement1ValueInt = 0
    if (achievement1Value == false) {
        achievement1ValueInt = 1
    }
    var achievement2Value by remember { mutableStateOf(sharedPreferencesManager.getAdditionPoints()) }
    var achievement3Value by remember { mutableStateOf(sharedPreferencesManager.getSubtractionPoints()) }
    var achievement4Value by remember { mutableStateOf(sharedPreferencesManager.getMultiplicationPoints()) }
    var achievement5Value by remember { mutableStateOf(sharedPreferencesManager.getDivisionPoints()) }

    //Ahievement show booleans
    var achievement1Bool by remember { mutableStateOf(sharedPreferencesManager.getAchi1()) }
    var achievement2Bool by remember { mutableStateOf(sharedPreferencesManager.getAchi2()) }
    var achievement3Bool by remember { mutableStateOf(sharedPreferencesManager.getAchi3()) }
    var achievement4Bool by remember { mutableStateOf(sharedPreferencesManager.getAchi4()) }
    var achievement5Bool by remember { mutableStateOf(sharedPreferencesManager.getAchi5()) }




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
                    .verticalScroll(rememberScrollState())
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
                    // achievement 1
                    if (showAchievement1.value && achievement1Bool == false) {
                        Column {
                            Text(
                                text = "Åben appen for første gang",
                                modifier = Modifier.padding(vertical = 5.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                contentAlignment = Alignment.Center,
                            ) {
                                LinearProgressIndicator(
                                    progress = achievement1ValueInt / 1f, // Calculate the progress
                                    color = Purple40, // Customize the color of the progress bar
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(20.dp)
                                        .clip(RoundedCornerShape(5.dp)) // Apply rounded corners

                                )
                                Text(
                                    text = "${achievement1ValueInt}/${1}",
                                    modifier = Modifier
                                        .padding(vertical = 5.dp)
                                        .wrapContentHeight(align = Alignment.CenterVertically),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                )
                                //Text contour
                                Text(
                                    text = "${achievement1ValueInt}/${1}",
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
                            if (achievement1ValueInt >= 1) {
                                // Button to remove the current achievement
                                Button(modifier = Modifier.height(30.dp),
                                    onClick = {
                                        showAchievement1.value = false
                                        sharedPreferencesManager.saveAchi1(true)
                                    }) {
                                    Text(text = "Fjern denne præstation", fontSize = 10.sp)
                                }
                            }
                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }
                    }

                    // achievement 2
                    if (showAchievement2.value && achievement2Bool == false) {
                        Column {
                            Text(
                                text = "Få 10 points i plus regnestykker (+)",
                                modifier = Modifier.padding(vertical = 5.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                contentAlignment = Alignment.Center,
                            ) {
                                LinearProgressIndicator(
                                    progress = achievement2Value / 10f, // Calculate the progress
                                    color = Purple40, // Customize the color of the progress bar
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(20.dp)
                                        .clip(RoundedCornerShape(5.dp)) // Apply rounded corners

                                )
                                Text(
                                    text = "${achievement2Value}/${10}",
                                    modifier = Modifier
                                        .padding(vertical = 5.dp)
                                        .wrapContentHeight(align = Alignment.CenterVertically),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                )
                                //Text contour
                                Text(
                                    text = "${achievement2Value}/${10}",
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
                            if (achievement2Value >= 10) {
                                // Button to remove the current achievement
                                Button(modifier = Modifier.height(30.dp),
                                    onClick = {
                                        showAchievement2.value = false
                                        sharedPreferencesManager.saveAchi2(true)
                                    }) {
                                    Text(text = "Fjern denne præstation", fontSize = 10.sp)
                                }
                            }
                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }
                    }


                    // achievement 3
                    if (showAchievement3.value && achievement3Bool == false) {
                        Column {
                            Text(
                                text = "Få 10 points i minus regnestykker (-)",
                                modifier = Modifier.padding(vertical = 5.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                contentAlignment = Alignment.Center,
                            ) {
                                LinearProgressIndicator(
                                    progress = achievement3Value / 10f, // Calculate the progress
                                    color = Purple40, // Customize the color of the progress bar
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(20.dp)
                                        .clip(RoundedCornerShape(5.dp)) // Apply rounded corners

                                )
                                Text(
                                    text = "${achievement3Value}/${10}",
                                    modifier = Modifier
                                        .padding(vertical = 5.dp)
                                        .wrapContentHeight(align = Alignment.CenterVertically),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                )
                                //Text contour
                                Text(
                                    text = "${achievement3Value}/${10}",
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
                            if (achievement3Value >= 10) {
                                // Button to remove the current achievement
                                Button(modifier = Modifier.height(30.dp),
                                    onClick = {
                                        showAchievement3.value = false
                                        sharedPreferencesManager.saveAchi3(true)
                                    }) {
                                    Text(text = "Fjern denne præstation", fontSize = 10.sp)
                                }
                            }
                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }
                    }


                    // achievement 4
                    if (showAchievement4.value && achievement4Bool == false) {
                        Column {
                            Text(
                                text = "Få 10 points i gange regnestykker (×)",
                                modifier = Modifier.padding(vertical = 5.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                contentAlignment = Alignment.Center,
                            ) {
                                LinearProgressIndicator(
                                    progress = achievement4Value / 10f, // Calculate the progress
                                    color = Purple40, // Customize the color of the progress bar
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(20.dp)
                                        .clip(RoundedCornerShape(5.dp)) // Apply rounded corners

                                )
                                Text(
                                    text = "${achievement4Value}/${10}",
                                    modifier = Modifier
                                        .padding(vertical = 5.dp)
                                        .wrapContentHeight(align = Alignment.CenterVertically),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                )
                                //Text contour
                                Text(
                                    text = "${achievement4Value}/${10}",
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
                            if (achievement4Value >= 10) {
                                // Button to remove the current achievement
                                Button(modifier = Modifier.height(30.dp),
                                    onClick = {
                                        showAchievement4.value = false
                                        sharedPreferencesManager.saveAchi4(true)
                                    }) {
                                    Text(text = "Fjern denne præstation", fontSize = 10.sp)
                                }
                            }
                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }
                    }


                    // achievement 5
                    if (showAchievement5.value && achievement5Bool == false) {
                        Column {
                            Text(
                                text = "Få 10 points i division regnestykker (÷)",
                                modifier = Modifier.padding(vertical = 5.dp),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                contentAlignment = Alignment.Center,
                            ) {
                                LinearProgressIndicator(
                                    progress = achievement5Value / 10f, // Calculate the progress
                                    color = Purple40, // Customize the color of the progress bar
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(20.dp)
                                        .clip(RoundedCornerShape(5.dp)) // Apply rounded corners

                                )
                                Text(
                                    text = "${achievement5Value}/${10}",
                                    modifier = Modifier
                                        .padding(vertical = 5.dp)
                                        .wrapContentHeight(align = Alignment.CenterVertically),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                )
                                //Text contour
                                Text(
                                    text = "${achievement5Value}/${10}",
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
                            if (achievement5Value >= 10) {
                                // Button to remove the current achievement
                                Button(modifier = Modifier.height(30.dp),
                                    onClick = {
                                        showAchievement5.value = false
                                        sharedPreferencesManager.saveAchi5(true)
                                    }) {
                                    Text(text = "Fjern denne præstation", fontSize = 10.sp)
                                }
                            }
                            Divider(
                                color = Color.Black,
                                thickness = 2.dp,
                                modifier = Modifier.padding(vertical = 5.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}