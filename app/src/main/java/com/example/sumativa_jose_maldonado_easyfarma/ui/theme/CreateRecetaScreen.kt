package com.example.sumativa_jose_maldonado_easyfarma.ui.theme

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sumativa_jose_maldonado_easyfarma.DoctorDBHelper
import com.example.sumativa_jose_maldonado_easyfarma.model.Receta
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CreateRecetaScreen(navController: NavController, doctorId: Long) {
    val context = LocalContext.current
    val dbHelper = remember { DoctorDBHelper(context) }

    // Campos de la receta
    var patientName by remember { mutableStateOf("") }
    var patientRut by remember { mutableStateOf("") }
    var medication by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Crear Receta",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(bottom = 24.dp)
                .align(Alignment.Start)
        )

        // --- Tarjeta de Creación ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                Text("Detalles del Paciente", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))

                OutlinedTextField(
                    value = patientName, onValueChange = { patientName = it }, label = { Text("Nombre del Paciente") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                )

                OutlinedTextField(
                    value = patientRut, onValueChange = { patientRut = it }, label = { Text("RUT del Paciente") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
                )

                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                Text("Detalles de la Medicación", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))

                OutlinedTextField(
                    value = medication, onValueChange = { medication = it }, label = { Text("Medicamento") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                )

                OutlinedTextField(
                    value = dosage, onValueChange = { dosage = it }, label = { Text("Dosis") },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                )

                OutlinedTextField(
                    value = instructions, onValueChange = { instructions = it }, label = { Text("Instrucciones/Notas") },
                    modifier = Modifier.fillMaxWidth().height(100.dp), singleLine = false
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de Guardar Receta
        Button(
            onClick = {
                if (patientName.isBlank() || patientRut.isBlank() || medication.isBlank() || dosage.isBlank()) {
                    Toast.makeText(context, "Por favor, completa los campos obligatorios.", Toast.LENGTH_SHORT).show()
                } else {
                    val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

                    val newReceta = Receta(
                        doctorId = doctorId,
                        patientName = patientName.trim(),
                        patientRut = patientRut.trim(),
                        medication = medication.trim(),
                        dosage = dosage.trim(),
                        instructions = instructions.trim(),
                        date = currentDate
                    )

                    val id = dbHelper.addReceta(newReceta)

                    if (id > 0) {
                        Toast.makeText(context, "Receta guardada con éxito.", Toast.LENGTH_LONG).show()
                        navController.popBackStack() // Regresa a la vista anterior (Home)
                    } else {
                        Toast.makeText(context, "Error al guardar la receta.", Toast.LENGTH_LONG).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Guardar Receta", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}