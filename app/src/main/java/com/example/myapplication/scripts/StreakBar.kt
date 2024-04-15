package com.example.myapplication.scripts

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

//Streak bar
@Composable
fun StreakBar(streak: Int) {
    val streakNeeded = 5
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val streakIconPosition = remember { mutableStateOf(Offset.Zero) }
    val rotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxSize(), // This will make the Box fill its parent
            contentAlignment = Alignment.TopCenter
    ) {
    Box(
        modifier = Modifier
            .size(100.dp) // Size of streak icon
            .padding(top = 20.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.streak_icon),
            contentDescription = "Streak Icon",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    rotationZ = if (streak >= 0) rotation * (streak / 10f) else 0f //Makes the icon shake
                }
                .onGloballyPositioned { coordinates ->
                    streakIconPosition.value = coordinates.positionInRoot()
                }
        )
        Text(
            text = "$streak",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
                .padding(top = 40.dp),
            color = Color.Black
        )
    }
    }
    // Create a list of particles
    val particles = remember { mutableStateListOf<Particle>() }
    // Define the colors
    val colors = listOf(Color.Red, Color.Yellow, Color(0xFFFFA500)) // 0xFFFFA500 is the hex code for orange
    // Get the icon's size in pixels
    val iconSizePx = with(LocalDensity.current) { 100.dp.toPx() }
    // Update particles
    LaunchedEffect(streak) {
        if (streak >= streakNeeded) {
            // Continuously create new particles
            while (true) {
                // Add a new particle at a random position around the center of the streak icon
                particles.add(
                    Particle(
                        x = streakIconPosition.value.x + iconSizePx / 2 + (Random.nextFloat() * 150f - 75f), // Add a random offset to the x coordinate
                        y = streakIconPosition.value.y + iconSizePx / 2 + (Random.nextFloat() * 150f - 75f), // Add a random offset to the y coordinate
                        speed = Random.nextFloat() * 5f,
                        radius = Random.nextFloat() * 10f,
                        alpha = 1f,
                        color = colors.random()
                    )
                )

                // Animate particles
                particles.forEach { particle ->
                    particle.y -= particle.speed
                    particle.alpha -= 0.05f
                }
                particles.removeAll { particle -> particle.alpha <= 0 }

                // Wait for a random interval before creating the next particle
                delay((Random.nextFloat() * 200).toLong())  // Delay to create a new particle multiple times per second
            }
        }
        particles.removeAll {true} // Remove particles when streak is less than or equal to 0
    }

    // Draw particles
    Canvas(modifier = Modifier.fillMaxSize()) {
        particles.forEach { particle ->
            drawCircle(
                color = particle.color.copy(alpha = particle.alpha), // Use the color of the particle
                radius = particle.radius,
                center = Offset(particle.x, particle.y)
            )
        }
    }
}
//Particle class is defined here
data class Particle(
    var x: Float,
    var y: Float,
    var speed: Float,
    var radius: Float,
    var alpha: Float,
    var color: Color
)

@Preview(showBackground = true)
@Composable
private fun MulFunctionPreview() {
    MyApplicationTheme {
        StreakBar(5)
    }
}