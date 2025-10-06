package com.example.sumativa_jose_maldonado_easyfarma.ui.theme

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sumativa_jose_maldonado_easyfarma.util.SettingsViewModel
import com.example.sumativa_jose_maldonado_easyfarma.ui.theme.SettingsControls
import com.example.sumativa_jose_maldonado_easyfarma.DoctorDBHelper
import com.example.sumativa_jose_maldonado_easyfarma.model.Doctor



@Composable
fun HomeScreen(navController: NavController, settingsViewModel: SettingsViewModel, doctorId: Long) {
    val context = LocalContext.current

    val dbHelper = remember { DoctorDBHelper(context) }
    var doctor by remember { mutableStateOf<Doctor?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }
    var showInfoDialog by remember { mutableStateOf(false) }

    // Cargar los datos del médico al entrar en la pantalla
    LaunchedEffect(doctorId) {
        if (doctorId != -1L) {
            doctor = dbHelper.getDoctorById(doctorId)
            if (doctor == null) {
                // Si no se encuentra el médico, forzar el cierre de sesión
                Toast.makeText(context, "Sesión expirada o datos no encontrados.", Toast.LENGTH_LONG).show()
                navController.navigate("login") { popUpTo(0) }
            }
        }
    }

    // Mostrar un indicador de carga mientras se obtienen los datos
    if (doctor == null && doctorId != -1L) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(/* color = brightBlue */)
        }
        return
    }

    // Usar el doctor cargado o un objeto Doctor por defecto
    val currentDoctor = doctor ?: Doctor(id = doctorId, name = "Invitado", lastName = "", rut = "", email = "", passwordHash = "", specialization = "N/A")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // --- 1. HEADER PERSONALIZADO ---
        Text(
            text = "Hola, Dr(a). ${currentDoctor.lastName}",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.Start)
        )
        Text(
            text = "Especialización: ${currentDoctor.specialization}",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.Start)
        )

        // --- 2. DASHBOARD DE ACCESIBILIDAD ---
        SettingsControls(viewModel = settingsViewModel)

        Spacer(modifier = Modifier.height(32.dp))

        // --- 3. GESTIÓN DE PERFIL ---
        Text(
            text = "Mi Perfil",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.Start)
        )

        // Fila de Tarjetas de Perfil/CRUD
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Botón 1: Ver Perfil - Abre el Diálogo
            ActionButton(
                label = "Ver Info",
                icon = Icons.Filled.Person,
                onClick = { showInfoDialog = true }, // Esto debe cambiar el estado a 'true'
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            // Botón 2: Editar Perfil - Abre el Diálogo
            ActionButton(
                label = "Editar Perfil",
                icon = Icons.Filled.Edit,
                onClick = { showEditDialog = true }, // Esto debe cambiar el estado a 'true'
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            // Botón 3: Eliminar Cuenta - Ejecuta lógica y navega
            ActionButton(
                label = "Eliminar",
                icon = Icons.Filled.Delete,
                onClick = {
                    currentDoctor.let {
                        if (it.id > 0) {
                            dbHelper.deleteDoctor(it.id)
                            Toast.makeText(context, "Cuenta eliminada.", Toast.LENGTH_LONG).show()
                            navController.navigate("login") { popUpTo(0) }
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                tintColor = MaterialTheme.colorScheme.error
            )
        }


        // --- ACCIONES PRINCIPALES (Escribir, Hablar, BuscarDispositivo) ---
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Easy Receta",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.Start)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Botón Escribir (Navega a la creación de receta)
            ActionButton(
                label = "Escribir",
                icon = Icons.Filled.Create,

                onClick = { navController.navigate("recetas_list/${currentDoctor.id}") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))

            // Botón Hablar:
            ActionButton(
                label = "Hablar",
                icon = Icons.Filled.Mic,
                onClick = { navController.navigate("voice_input/${currentDoctor.id}") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))

            ActionButton(
                label = "Dispositivo",
                icon = Icons.Filled.GpsFixed,

                onClick = { navController.navigate("device_search") }, // <-- NAVEGACIÓN
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // --- 4. BOTÓN DE CERRAR SESIÓN ---
        TextButton(onClick = {
            navController.navigate("login") {
                popUpTo("home/{doctorId}") { inclusive = true }
            }
        }) {
            Text(
                "Cerrar Sesión",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}


// COMPONENTES REUSABLES


@Composable
fun ActionButton(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tintColor: Color = MaterialTheme.colorScheme.secondary
) {
    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = tintColor,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1
            )
        }
    }
}

@Composable
fun DoctorInfoDialog(doctor: Doctor, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Información del Dr(a). ${doctor.lastName}", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Nombre Completo: ${doctor.name} ${doctor.lastName}")
                Divider(Modifier.padding(vertical = 4.dp))
                Text("RUT: ${doctor.rut}")
                Text("Correo: ${doctor.email}")
                Text("Especialización: ${doctor.specialization}", fontWeight = FontWeight.SemiBold)
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("Cerrar") }
        }
    )
}

@Composable
fun EditProfileDialog(
    doctor: Doctor,
    onDismiss: () -> Unit,
    onSave: (Doctor) -> Unit
) {
    var name by remember { mutableStateOf(doctor.name) }
    var lastName by remember { mutableStateOf(doctor.lastName) }
    var specialization by remember { mutableStateOf(doctor.specialization) }
    var newPassword by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Perfil") },
        text = {
            Column {

            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedDoctor = doctor.copy(
                    name = name.trim(),
                    lastName = lastName.trim(),
                    specialization = specialization.trim(),

                    passwordHash = if (newPassword.isNotBlank()) newPassword else doctor.passwordHash
                )
                onSave(updatedDoctor)
            }) { Text("Guardar Cambios") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}