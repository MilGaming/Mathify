package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.myapplication.mathPages.AddActivity
import com.example.myapplication.mathPages.DivActivity
import com.example.myapplication.mathPages.MulActivity
import com.example.myapplication.mathPages.SubActivity
import com.example.myapplication.scripts.CustomTopBar
import com.example.myapplication.scripts.PreferencesManager
import com.example.myapplication.ui.theme.Purple40
import com.example.myapplication.ui.theme.Purple80
import com.example.myapplication.ui.theme.FortniteBlue
import com.example.myapplication.ui.theme.FortniteLightBlue
import com.example.myapplication.ui.theme.FortniteGreen
import com.example.myapplication.ui.theme.FortniteYellow
import com.example.myapplication.ui.theme.FortniteOrange


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
                .height(60.dp),
            ) {

            }

            Column(
                //Adds padding to button column at the top
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
                    .background(FortniteLightBlue),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(top = 100.dp, start = 30.dp)
                        .align(Alignment.Start),
                ) {
                    //Text contour
                    Text(
                        text = "Vælg en udfordring:",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        style = TextStyle.Default.copy(
                            fontSize = 36.sp,
                            drawStyle = Stroke(
                                miter = 50f,
                                width = 8f,
                                join = StrokeJoin.Round
                            )
                        )
                    )
                    Text(text = "Vælg en udfordring:",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray
                    )
                }
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
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF2C12E),
                                contentColor = Color.DarkGray
                            ),
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
                                    containerColor = FortniteYellow,
                                    contentColor = Color.DarkGray
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
                                color = Color.DarkGray,
                            )
                        }
                    }
                    //Whatever needs to be under button 1 to has to be added below here--------
                    this.AnimatedVisibility(visible = isExpandedB1.value) {
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(0.8f),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Plus statistik",
                                )
                                Divider(color = Color.Gray, thickness = 1.dp)
                                Text(
                                    text = "Points: ${preferencesManager.getAdditionPoints()}",
                                )
                                Text(
                                    text = "MMR: ${preferencesManager.getAddMMR()}",
                                )
                            }
                        }
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
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF29B2B),
                                contentColor = Color.DarkGray
                            ),
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
                                    containerColor = Color(0xFFF29B2B),
                                    contentColor = Color.DarkGray
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
                                color = Color.DarkGray,
                            )
                        }
                    }
                    //Whatever needs to be under button 2 to has to be added below here--------
                    this.AnimatedVisibility(visible = isExpandedB2.value) {
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(0.8f),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Minus statistik",
                                )
                                Divider(color = Color.Gray, thickness = 1.dp)
                                Text(
                                    text = "Points: ${preferencesManager.getSubtractionPoints()}",
                                )
                                Text(
                                    text = "MMR: ${preferencesManager.getSubMMR()}",
                                )
                            }
                        }
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
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF28727),
                                contentColor = Color.DarkGray
                            ),
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
                                    containerColor = Color(0xFFF28727),
                                    contentColor = Color.DarkGray
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
                                color = Color.DarkGray,
                            )
                        }
                    }
                    //Whatever needs to be under button 3 to has to be added below here--------
                    this.AnimatedVisibility(visible = isExpandedB3.value) {
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(0.8f),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Gange statistik",
                                )
                                Divider(color = Color.Gray, thickness = 1.dp)
                                Text(
                                    text = "Points: ${preferencesManager.getMultiplicationPoints()}",
                                )
                                Text(
                                    text = "MMR: ${preferencesManager.getMulMMR()}",
                                )
                            }
                        }
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
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF27528),
                                contentColor = Color.DarkGray
                            ),
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
                                    containerColor = Color(0xFFF27528),
                                    contentColor = Color.DarkGray
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
                                color = Color.DarkGray,
                            )
                        }
                    }
                    //Whatever needs to be under button 4 to has to be added below here--------
                    this.AnimatedVisibility(visible = isExpandedB4.value) {
                        Card(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(0.8f),
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Division statistik",
                                )
                                Divider(color = Color.Gray, thickness = 1.dp)
                                Text(
                                    text = "Points: ${preferencesManager.getDivisionPoints()}",
                                )
                                Text(
                                    text = "MMR: ${preferencesManager.getDivMMR()}",
                                )
                            }
                        }
                    }
                }
            }

        }
        // Display the total score in the top right corner
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.Transparent)
                    .width(150.dp)
                    .height(80.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Samlede points",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = totalScore.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
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
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = FortniteYellow,
                    contentColor = Color.DarkGray
                ),
            ) {
                Text("Forstået")
            }
        }
    )
}