package com.example.sumativa_jose_maldonado_easyfarma.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.SettingsBrightness
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sumativa_jose_maldonado_easyfarma.util.SettingsViewModel

@Composable
fun SettingsControls(viewModel: SettingsViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de la sección
            Text(
                "Controles de Accesibilidad",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // --- 1. CONTROL DE MODO NOCTURNO ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Modo Nocturno",
                    style = MaterialTheme.typography.bodyLarge
                )
                Switch(
                    checked = viewModel.isDarkMode,
                    onCheckedChange = { viewModel.toggleDarkMode() },
                    thumbContent = {
                        Icon(
                            Icons.Filled.SettingsBrightness,
                            contentDescription = "Modo Noche",
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                            tint = if (viewModel.isDarkMode) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondary
                        )
                    }
                )
            }
            Divider(modifier = Modifier.padding(vertical = 10.dp))

            // --- 2. CONTROL DE TAMAÑO DE FUENTE ---
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Ajustar Tamaño de Fuente",
                    style = MaterialTheme.typography.bodyLarge
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Botón para disminuir
                    Button(
                        onClick = { viewModel.decreaseFontSize() },
                        enabled = viewModel.fontScaleFactor > 0.8f, // Deshabilita si es el mínimo
                        modifier = Modifier.size(40.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(Icons.Filled.Remove, contentDescription = "Disminuir Fuente")
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    // Indicador de nivel de fuente
                    Text(
                        text = "${(viewModel.fontScaleFactor * 100).toInt()}%",
                        modifier = Modifier.width(40.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Botón para aumentar
                    Button(
                        onClick = { viewModel.increaseFontSize() },
                        enabled = viewModel.fontScaleFactor < 1.4f, // Deshabilita si es el máximo
                        modifier = Modifier.size(40.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Aumentar Fuente")
                    }
                }
            }
        }
    }
}