package com.example.myapplication.scripts

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlin.random.Random


data class Achievement(val index: Int, val name: String, val currentProgress: Int, val goalProgress: Int)


    @Composable
    fun AchievementPopup(openDialog: MutableState<Boolean>, isHeldDown: MutableState<Boolean>){
        val cornerSize = 10.dp //box specs
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        val sharedPreferencesManager = PreferencesManager(context)
        val achievements = remember { mutableStateListOf(*sharedPreferencesManager.getAchievements().toTypedArray()) }
        val achievementIndex = remember { mutableIntStateOf(sharedPreferencesManager.getAchievementIndex()) }


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
                        // Loop through the achievements and display them
                        for ((index, achievement) in achievements.withIndex()) {
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
                                // Button to remove the current achievement
                                Button(modifier = Modifier.height(30.dp),
                                    onClick = {
                                        val achievementToRemove = achievements.firstOrNull { it.index == achievement.index }
                                        if (achievementToRemove != null) {
                                            achievements.remove(achievementToRemove)
                                            sharedPreferencesManager.saveAchievements(achievements)
                                        }
                                    }) {
                                    Text(text = "Fjern denne præstation", fontSize = 10.sp)
                                }
                                Divider(
                                    color = Color.Black,
                                    thickness = 2.dp,
                                    modifier = Modifier.padding(vertical = 5.dp)
                                )
                            }
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            // Button to add a new achievement for testing
                            Button(onClick = {
                                val newAchievement = Achievement(achievementIndex.intValue, "Præstation (${achievementIndex.intValue})",  Random.nextInt(0,100), 100)
                                achievements.add(newAchievement)
                                sharedPreferencesManager.saveAchievements(achievements)
                                achievementIndex.intValue++  // Increment the independent index tracker
                                sharedPreferencesManager.saveAchievementIndex(achievementIndex.intValue)  // Save the updated index
                            }) {
                                Text("Tilføj Præstation")
                            }
                            // Button to remove the specific achievement for testing
                            Button(onClick = {
                                val achievementToRemove = achievements.firstOrNull { it.index == 4 } // Replace 0 with the specific index of the achievement you want to remove
                                if (achievementToRemove != null) {
                                    achievements.remove(achievementToRemove)
                                    sharedPreferencesManager.saveAchievements(achievements)
                                }
                            }) {
                                Text("Fjern Præstation")
                            }
                            Spacer(modifier = Modifier.height(75.dp))
                        }

                    }
                }
            }
        }
    }