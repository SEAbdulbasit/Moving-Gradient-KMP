package com.abdulbasit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import movinggradient.composeapp.generated.resources.Res
import movinggradient.composeapp.generated.resources.compose_multiplatform
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.animation.core.LinearEasing as LinearEasing1

@Composable
@Preview
fun App() {
    MaterialTheme {
        FluidMeshGradient()
    }
}

@Composable
fun FluidMeshGradient(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "gradient")

    // Angle animations for smoother movement
    val angle1 = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(30000, easing = LinearEasing1),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle1"
    )

    val angle2 = infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(25000, easing = LinearEasing1),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle2"
    )

    // Color animations
    val color1 = infiniteTransition.animateColor(
        initialValue = Color(0xFFFF0080), // Hot pink
        targetValue = Color(0xFF7928CA), // Purple
        animationSpec = infiniteRepeatable(
            animation = tween(8000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color1"
    )

    val color2 = infiniteTransition.animateColor(
        initialValue = Color(0xFF00E1FF), // Cyan
        targetValue = Color(0xFFFF4D4D), // Red
        animationSpec = infiniteRepeatable(
            animation = tween(12000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color2"
    )

    val color3 = infiniteTransition.animateColor(
        initialValue = Color(0xFF00FF66), // Neon green
        targetValue = Color(0xFFFF8A00), // Orange
        animationSpec = infiniteRepeatable(
            animation = tween(10000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "color3"
    )

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val center = Offset(canvasWidth / 2f, canvasHeight / 2f)
        val radius = maxOf(canvasWidth, canvasHeight) * 1.5f // Much larger radius

        // Calculate moving points for gradients with wider movement
        val point1 = Offset(
            center.x + cos(angle1.value * PI.toFloat() / 180f) * radius * 0.5f,
            center.y + sin(angle1.value * PI.toFloat() / 180f) * radius * 0.5f
        )

        val point2 = Offset(
            center.x + cos(angle2.value * PI.toFloat() / 180f) * radius * 0.5f,
            center.y + sin(angle2.value * PI.toFloat() / 180f) * radius * 0.5f
        )

        // First gradient layer
        val gradientBrush1 = Brush.radialGradient(
            colors = listOf(
                color1.value.copy(alpha = 0.7f),
                color2.value.copy(alpha = 0.4f),
                Color.Transparent
            ),
            center = point1,
            radius = radius
        )

        // Second gradient layer
        val gradientBrush2 = Brush.radialGradient(
            colors = listOf(
                color2.value.copy(alpha = 0.7f),
                color3.value.copy(alpha = 0.4f),
                Color.Transparent
            ),
            center = point2,
            radius = radius
        )

        // Center static gradient for consistent saturation
        val centerGradient = Brush.radialGradient(
            colors = listOf(
                color3.value.copy(alpha = 0.5f),
                color1.value.copy(alpha = 0.3f),
                Color.Transparent
            ),
            center = center,
            radius = radius * 0.8f
        )

        // Draw the gradients
        drawRect(brush = gradientBrush1, blendMode = BlendMode.Screen)
        drawRect(brush = gradientBrush2, blendMode = BlendMode.Screen)
        drawRect(brush = centerGradient, blendMode = BlendMode.Screen)
    }
}