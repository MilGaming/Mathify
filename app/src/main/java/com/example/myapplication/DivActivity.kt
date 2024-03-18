package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.myapplication.ui.theme.MyApplicationTheme

class DivActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DivFunction()
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DivFunction() {
    var isHeldDown by remember { mutableStateOf(false) } // to see if menu open
    val openDialog = remember { mutableStateOf(false) } // for popup
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray),
                title = { Text("Mathify") },
                actions = {
                    IconButton(onClick = { //home button
                        val intent = Intent(context,MainActivity::class.java)
                        context.startActivity(intent)
                    }) {
                        Icon(Icons.Filled.Home, contentDescription = "Home")
                    }
                    Surface(
                        color = if (isHeldDown) Color.Gray else Color.Transparent,

                        shape = CircleShape
                    ) {
                        IconButton(onClick = { //aceivment pop up menu
                            isHeldDown = !isHeldDown
                            openDialog.value = !openDialog.value
                        },

                            ) {
                            Icon(Icons.Filled.Star, contentDescription = "Trophy")
                        }
                    }

                    Box(
                        modifier = Modifier.offset(y = 15.dp)
                    ){
                        val popupWidth = 120.dp
                        val popupHeight = 130.dp //box specs
                        val cornerSize= 10.dp

                        if(openDialog.value){ //open the box
                            Popup(
                                alignment = Alignment.TopStart, //here we mention the pos
                                properties = PopupProperties()
                            ) {
                                Box( //the box content
                                    modifier = Modifier
                                        .size(popupWidth, popupHeight)
                                        .padding(top = 2.dp)
                                        .background(Color.Green, RoundedCornerShape(cornerSize))
                                        .border(1.dp, Color.Black, RoundedCornerShape(cornerSize))

                                ){
                                    Column(
                                        modifier = Modifier.padding(horizontal = 10.dp),
                                        verticalArrangement = Arrangement.spacedBy(5.dp)

                                    ){
                                        Text(text = "Achivments1",
                                            modifier = Modifier.padding(vertical = 5.dp),
                                            fontSize = 16.sp
                                        )

                                        Divider(modifier = Modifier.border(1.dp, Color.Black))

                                        Text(text = "Achivments2",
                                            modifier = Modifier.padding(vertical = 5.dp),
                                            fontSize = 16.sp
                                        )

                                        Divider(modifier = Modifier.border(1.dp, Color.Black))

                                        Text(text = "Achivments3",
                                            modifier = Modifier.padding(vertical = 5.dp),
                                            fontSize = 16.sp
                                        )


                                    }



                                }

                            }
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
}


@Preview(showBackground = true)
@Composable
private fun DivFunctionPreview() {
    MyApplicationTheme {
        DivFunction()
    }
}