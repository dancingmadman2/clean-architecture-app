package com.example.movieTracker.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun SetSystemBarsColor(statusBarColor: Color, navigationBarColor: Color) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as android.app.Activity).window
            window.statusBarColor = statusBarColor.toArgb()
            window.navigationBarColor = navigationBarColor.toArgb()

            // Optional: Set light/dark icons based on color luminance
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                statusBarColor.luminance() > 0.5
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                navigationBarColor.luminance() > 0.5
        }
    }
}