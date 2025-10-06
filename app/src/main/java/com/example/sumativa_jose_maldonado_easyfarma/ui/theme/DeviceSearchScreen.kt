package com.example.sumativa_jose_maldonado_easyfarma.ui.theme

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationSearching
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

@Composable
fun DeviceSearchScreen(navController: NavController) {
    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    var statusText by remember { mutableStateOf("Presiona buscar para iniciar la búsqueda de dispositivos IoT.") }

    // 1. Verificación inicial del permiso
    var locationPermissionGranted by remember {
        mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }



    val startSearch: () -> Unit = {
        statusText = "Buscando dispositivos cerca de ti..."
        // Pide la última ubicación conocida
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                // Simulación de que se encontró un dispositivo
                statusText = "Dispositivo EasyFarma encontrado cerca de Lat ${location.latitude}, Lon ${location.longitude}. Listo para sincronizar."
            } else {
                statusText = "No se pudo obtener la ubicación actual. Asegúrate de que el GPS esté encendido."
            }
        }.addOnFailureListener {
            statusText = "Error de GPS: No se pudo obtener la ubicación."
        }
    }


    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        locationPermissionGranted = isGranted
        if (isGranted) {
            Toast.makeText(context, "Permiso concedido. Inicia la búsqueda.", Toast.LENGTH_SHORT).show()
            // Inicia la búsqueda inmediatamente después de conceder el permiso
            startSearch()
        } else {
            statusText = "Permiso de ubicación denegado. No se puede buscar el dispositivo."
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Filled.LocationSearching,
            contentDescription = "Buscar Dispositivo",
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Buscar Dispositivo IoT", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.primary)

        Card(
            modifier = Modifier.fillMaxWidth().height(100.dp).padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Box(Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                Text(statusText, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Button(
            // Al hacer clic: si tiene permiso, inicia búsqueda; si no, lo pide.
            onClick = {
                if (!locationPermissionGranted) {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                } else {
                    startSearch() // <-- Llamada directa a la función lambda
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Iniciar Búsqueda")
        }

        TextButton(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}