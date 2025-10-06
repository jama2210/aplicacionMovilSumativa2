package com.example.sumativa_jose_maldonado_easyfarma.ui.theme

import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.util.Locale
import android.app.Activity



@Composable
fun VoiceInputScreen(navController: NavController, doctorId: Long) {
    val context = LocalContext.current
    var spokenText by remember { mutableStateOf("Toca el micrófono para dictar la información de la receta.") }
    var isListening by remember { mutableStateOf(false) }

    // Launcher para iniciar la actividad de reconocimiento de voz
    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        isListening = false

        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            // Obtenemos el texto transcrito
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!results.isNullOrEmpty()) {
                spokenText = results[0] //
            }
        } else if (result.resultCode == RecognizerIntent.RESULT_NO_MATCH) {
            spokenText = "No se detectó discurso. Inténtalo de nuevo."
        } else if (result.resultCode == RecognizerIntent.RESULT_CLIENT_ERROR) {
            spokenText = "Error de conexión o configuración."
        }
    }

    val startListening = {
        // Configuramos la intención para el reconocimiento de voz
        isListening = true
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault()) // Usar el idioma local
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Dictando la receta...")
        }
        speechRecognizerLauncher.launch(intent)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Dictado por Voz",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Tarjeta para mostrar el texto transcrito
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Text(spokenText)
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Botón de Micrófono
        Button(
            onClick = startListening,
            enabled = !isListening,
            modifier = Modifier.size(100.dp),
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(Icons.Filled.Mic, contentDescription = "Grabar voz", modifier = Modifier.size(48.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isListening) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            Text("Escuchando...", color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Opción de enviar el texto a otra pantalla
        if (spokenText != "Toca el micrófono para dictar la información de la receta." && !isListening) {
            Button(
                onClick = {
                    Toast.makeText(context, "Texto copiado o listo para usar en una receta.", Toast.LENGTH_SHORT).show()
                    // Aquí se implementaría la lógica para llevar el texto a un formulario.
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Usar Texto Transcrito")
            }
        }

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Volver", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}