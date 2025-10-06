package com.example.sumativa_jose_maldonado_easyfarma.util

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    // Estado para el modo oscuro (true = Dark Mode)
    var isDarkMode by mutableStateOf(false)
        private set

    // Estado para el factor de escala de la fuente (1.0f = normal, 1.2f = grande)
    var fontScaleFactor by mutableStateOf(1.0f)
        private set

    fun toggleDarkMode() {
        isDarkMode = !isDarkMode
    }

    fun increaseFontSize() {
        // Lógica simple para aumentar la fuente
        if (fontScaleFactor < 1.4f) {
            fontScaleFactor += 0.2f
        }
    }

    fun decreaseFontSize() {
        // Lógica simple para disminuir la fuente
        if (fontScaleFactor > 0.8f) {
            fontScaleFactor -= 0.2f
        }
    }
}