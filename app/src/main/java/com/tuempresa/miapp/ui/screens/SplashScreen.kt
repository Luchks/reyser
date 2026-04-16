package com.tuempresa.miapp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController

import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay
import com.tuempresa.miapp.R   // IMPORTANTE

private const val SPLASH_DELAY = 1500L
private const val ANIMATION_DURATION = 800

@Composable
fun SplashScreen(navController: NavController) {
    var visible by remember { mutableStateOf(false) }
    var navigate by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(ANIMATION_DURATION),
        label = "splashAlpha"
    )

    LaunchedEffect(Unit) {
        visible = true              // Fade-in
        delay(SPLASH_DELAY)         // Mantener visible
        visible = false
        delay(ANIMATION_DURATION.toLong())
        navigate = true             // Navegar
    }

    LaunchedEffect(navigate) {
        if (navigate) {
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_lima_vip),
            contentDescription = "Logo Lima VIP Travel",
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha),
            contentScale = ContentScale.Fit   // Prueba Crop si quieres que llene todo
        )
    }
}
