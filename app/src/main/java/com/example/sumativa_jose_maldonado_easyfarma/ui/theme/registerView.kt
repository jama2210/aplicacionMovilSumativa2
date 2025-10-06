

package com.example.sumativa_jose_maldonado_easyfarma.ui.theme

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sumativa_jose_maldonado_easyfarma.model.User
import com.example.sumativa_jose_maldonado_easyfarma.util.validateRegistration
import com.example.sumativa_jose_maldonado_easyfarma.R
// IMPORTS ADICIONALES NECESARIOS
import androidx.compose.ui.graphics.Brush

import com.example.sumativa_jose_maldonado_easyfarma.DoctorDBHelper
import com.example.sumativa_jose_maldonado_easyfarma.model.Doctor

@Composable
fun RegisterScreen(navController: NavController, users: MutableList<User>) {
    val context = LocalContext.current
    // Instancia de SQLite
    val dbHelper = remember { DoctorDBHelper(context) }

    // CAMPOS DE REGISTRO DE MÉDICO
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var specialization by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(color1, color2, color3) // Usamos nombres estandarizados
                )
            )
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xCCFFFFFF) // 80% de opacidad
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo y texto
                Image(
                    painter = painterResource(id = R.drawable.logo_easy_receta),
                    contentDescription = "Logo Easy Receta",
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = "Registro de Médico", // Título actualizado
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                )


                // Campo de Nombre
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(focusedIndicatorColor = brightBlue, unfocusedIndicatorColor = mediumGray)
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Campo de Apellido
                TextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Apellido") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(focusedIndicatorColor = brightBlue, unfocusedIndicatorColor = mediumGray)
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Campo de RUT
                TextField(
                    value = rut,
                    onValueChange = { rut = it },
                    label = { Text("RUT") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(focusedIndicatorColor = brightBlue, unfocusedIndicatorColor = mediumGray)
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Campo de Especialización
                TextField(
                    value = specialization,
                    onValueChange = { specialization = it },
                    label = { Text("Especialización") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(focusedIndicatorColor = brightBlue, unfocusedIndicatorColor = mediumGray)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // ------------------ CAMPOS DE EMAIL Y CONTRASEÑA ------------------

                // Campo de correo electrónico
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(focusedIndicatorColor = brightBlue, unfocusedIndicatorColor = mediumGray)
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Campo de contraseña
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(focusedIndicatorColor = brightBlue, unfocusedIndicatorColor = mediumGray)
                )
                Spacer(modifier = Modifier.height(12.dp))

                // Campo de confirmar contraseña
                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(focusedIndicatorColor = brightBlue, unfocusedIndicatorColor = mediumGray)
                )
                Spacer(modifier = Modifier.height(24.dp))

                // Botón de Registro
                Button(
                    onClick = {
                        if (name.isBlank() || lastName.isBlank() || rut.isBlank() || email.isBlank() || password.isBlank() || specialization.isBlank()) {
                            Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show()
                        } else if (password != confirmPassword) {
                            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
                        } else if (dbHelper.isEmailRegistered(email)) {
                            Toast.makeText(context, "El correo ya está registrado", Toast.LENGTH_LONG).show()
                        } else {
                            val newDoctor = Doctor(
                                name = name.trim(),
                                lastName = lastName.trim(),
                                rut = rut.trim(),
                                email = email.trim(),
                                passwordHash = password, // Guardado simple
                                specialization = specialization.trim()
                            )
                            val id = dbHelper.addDoctor(newDoctor)
                            if (id > 0) {
                                Toast.makeText(context, "¡Doctor registrado correctamente!", Toast.LENGTH_LONG).show()
                                navController.navigate("login")
                            } else {
                                Toast.makeText(context, "Error al registrar: Correo o RUT duplicado.", Toast.LENGTH_LONG).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = brightBlue),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Registrarse", fontSize = 18.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Botón de Volver
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Volver", color = brightBlue)
                }
            }
        }
    }
}