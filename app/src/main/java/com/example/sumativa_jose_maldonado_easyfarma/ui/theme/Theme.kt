// Archivo: app/src/main/java/com/example/sumativa_jose_maldonado_easyfarma.ui.theme/Theme.kt

package com.example.sumativa_jose_maldonado_easyfarma.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density // Necesario para el escalado de la fuente

// ====================================================================
// 1. DEFINICIÓN DE COLORES DE EASY RECETA
// ====================================================================

// Colores primarios (de tu proyecto: #00154f, #013ee9, #fff)
val NavyBlue = Color(0xFF00154F)
val BrightBlue = Color(0xFF013EE9)
val White = Color(0xFFFFFFFF)
val DarkGray = Color(0xFF1C1B1F) // Usado para modo oscuro
val LightGrayBackground = Color(0xFFF0F0F0) // Usado para fondo Light

// ====================================================================
// 2. PALETAS DE COLORES (Light y Dark)
// ====================================================================

private val DarkColorScheme = darkColorScheme(
    primary = BrightBlue,
    onPrimary = White,
    secondary = NavyBlue,
    background = Color(0xFF121212), // Fondo oscuro
    surface = Color(0xFF1D1D1D),
    onBackground = White, // Texto en blanco para el fondo oscuro
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = NavyBlue, // El azul marino como color principal
    onPrimary = White,
    secondary = BrightBlue,
    background = LightGrayBackground, // Usamos un gris claro de fondo
    surface = White,
    onBackground = DarkGray, // Texto oscuro
    onSurface = DarkGray
)

// ====================================================================
// 3. FUNCIÓN DEL TEMA MODIFICADA
// ====================================================================

@Composable
fun Sumativa_jose_maldonado_easyFarmaTheme(

    darkTheme: Boolean,
    fontScaleFactor: Float = 1.0f, // Nuevo parámetro para el control de fuente
    dynamicColor: Boolean = false, // Desactivamos el color dinámico por ahora para usar los colores fijos
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Opción de color dinámico
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // Usamos nuestras paletas fijas según el estado 'darkTheme'
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Aplicación del factor de escala de la fuente
    // Aplicación del factor de escala de la fuente
    CompositionLocalProvider(
        LocalDensity provides Density(
            density = LocalDensity.current.density,
            fontScale = fontScaleFactor
        )
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}