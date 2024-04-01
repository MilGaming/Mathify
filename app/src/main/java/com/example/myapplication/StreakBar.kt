package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

//Streak bar
@Composable
fun StreakBar(streak: Int) {
    val streakNeeded = 5
    val infiniteTransition = rememberInfiniteTransition()
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
            .fillMaxWidth()
            .padding(top = 50.dp, start = 16.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Current Streak: $streak",
                fontSize = 24.sp
            )
            Image(
                painter = painterResource(id = R.drawable.streak_icon),
                contentDescription = "Streak Icon",
                modifier = Modifier
                    .size(65.dp)
                    .graphicsLayer {
                        rotationZ = if (streak >= streakNeeded) rotation * (streak / 5f) else 0f
                    }
                    .onGloballyPositioned { coordinates ->
                        streakIconPosition.value = coordinates.positionInRoot()
                    }
            )
        }
    }
    // Create a list of particles
    val particles = remember { mutableStateListOf<Particle>() }
    // Define the colors
    val colors = listOf(Color.Red, Color.Yellow, Color(0xFFFFA500)) // 0xFFFFA500 is the hex code for orange
    // Get the icon's size in pixels
    val iconSizePx = with(LocalDensity.current) { 65.dp.toPx() }
    // Update particles
    LaunchedEffect(streak) {
        if (streak >= streakNeeded) {
            // Continuously create new particles
            while (true) {
                // Add a new particle at a random position around the center of the streak icon
                particles.add(
                    Particle(
                        x = streakIconPosition.value.x + iconSizePx / 2 + (Random.nextFloat() * 50f - 25f), // Add a random offset to the x coordinate
                        y = streakIconPosition.value.y + iconSizePx / 2 + (Random.nextFloat() * 50f - 25f), // Add a random offset to the y coordinate
                        speed = Random.nextFloat() * 5f,
                        radius = Random.nextFloat() * 5f,
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