package com.example.sumativa_jose_maldonado_easyfarma.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.sumativa_jose_maldonado_easyfarma.DoctorDBHelper
import com.example.sumativa_jose_maldonado_easyfarma.model.Receta
import androidx.compose.ui.Alignment

@Composable
fun RecetasListScreen(navController: NavController, doctorId: Long) {
    val context = LocalContext.current

    val dbHelper = remember { DoctorDBHelper(context) }

    // Estado mutable para guardar la lista de recetas
    var recetas by remember { mutableStateOf(emptyList<Receta>()) }

    // Función para recargar las recetas
    val loadRecetas: () -> Unit = {
        recetas = dbHelper.getAllRecetasByDoctor(doctorId)
    }

    // Cargar las recetas al iniciar la pantalla
    LaunchedEffect(key1 = Unit) {
        loadRecetas()
    }

    Scaffold(
        // Botón Flotante para crear una nueva receta
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Navega a la pantalla de creación de recetas
                    navController.navigate("create_receta/$doctorId")
                },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Filled.Add, "Crear nueva receta")
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Mis Recetas",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }


            // Verifica si la lista está vacía
            if (recetas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aún no has creado ninguna receta.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                // Muestra la lista de recetas
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(recetas) { receta ->
                        RecetaListItem(receta = receta, onClick = {
                            // TODO: Implementar navegación a la vista de detalle de receta para Editar/Eliminar
                        })
                    }
                }
            }
        }
    }
}

// ------------------------------------------------------------------
// COMPONENTES REUSABLES
// ------------------------------------------------------------------

@Composable
fun RecetaListItem(receta: Receta, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = receta.patientName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Med: ${receta.medication} (${receta.dosage})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Fecha: ${receta.date}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                Icons.Filled.ChevronRight,
                contentDescription = "Ver detalles",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}