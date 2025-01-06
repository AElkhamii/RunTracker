package com.example.core.presentation.designsystem

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val DarkColorScheme = darkColorScheme(
    primary = RuniqueGreen,
    background = RuniqueBlack,
    surface = RuniqueDarkGray,
    secondary = RuniqueWhite,
    tertiary = RuniqueWhite,
    primaryContainer = RuniqueGreen30,
    /* This section contains colors on top of main colors of the app */
    onPrimary = RuniqueBlack,
    onBackground = RuniqueWhite,
    onSurface = RuniqueWhite,
    onSurfaceVariant = RuniqueGray
)

@Composable
fun RunTrackerTheme(
    // We do not support switching between themes.
    //  darkTheme: Boolean = isSystemInDarkTheme(),

    // Dynamic color is available on Android 12+, we do not support that as well in this app
    //  dynamicColor: Boolean = true,
    content: @Composable () -> Unit
){
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode){
        /* It is used to adjust status bar color automatically
         * we have side effect since we execute non compose code inside a composable function
         * and side effect used to execute this code after each successful recomposition */
        SideEffect {
            val window = (view.context as Activity).window
            // we will not use this in our app because we want transparent status bar
            // window.statusBarColor = colorScheme.primary.toArgb()

            // this to make sure icons appears as light icons (icons such as battery percentage in the top of your phone)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}