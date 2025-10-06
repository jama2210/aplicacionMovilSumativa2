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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sumativa_jose_maldonado_easyfarma.model.User
import com.example.sumativa_jose_maldonado_easyfarma.R
import com.example.sumativa_jose_maldonado_easyfarma.util.validateUserLogin
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import com.example.sumativa_jose_maldonado_easyfarma.util.SettingsViewModel
import androidx.compose.animation.*
import androidx.compose.animation.core.tween

import com.example.sumativa_jose_maldonado_easyfarma.DoctorDBHelper


val navyBlue = Color(0xFF00154F)
val brightBlue = Color(0xFF013EE9)
val mediumGray = Color(0xFFE0E0E0)

// Colores del degradado
val color1 = Color(0xFFFFFFFF)  // Blanco puro
val color2 = Color(0xCC2A5D9B)  // Azul intermedio suave
val color3 = Color(0xFF2A5D9B)  // Azul Medio, más claro que el NavyBlue.


@Composable
fun LoginScreen(navController: NavController, users: List<User>, settingsViewModel: SettingsViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Instancia de SQLite
    val dbHelper = remember { DoctorDBHelper(context) }

    // Animación de entrada
    var showContent by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        showContent = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    // USAMOS LOS NOMBRES CENTRALIZADOS
                    colors = listOf(color1, color2, color3)
                )
            )
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Contenido envuelto en la animación de deslizamiento
        AnimatedVisibility(
            visible = showContent,
            enter = slideInVertically(
                initialOffsetY = { -it / 2 },
                animationSpec = tween(durationMillis = 600)
            ) + fadeIn(animationSpec = tween(durationMillis = 600)),
            exit = slideOutVertically(
                targetOffsetY = { -it / 2 },
                animationSpec = tween(durationMillis = 300)
            ) + fadeOut()
        ) {

            // --- INICIO DE LA CARD CON EFECTO GLASS ---
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xCCFFFFFF)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo y texto
                    Image(
                        painter = painterResource(id = R.drawable.logo_easy_receta),
                        contentDescription = "Logo Easy Receta",
                        modifier = Modifier.size(100.dp)
                    )
                    Text(
                        text = "Iniciar Sesión (Médico)", // Título actualizado
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
                    )

                    // Campo de correo electrónico
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo Electrónico") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = brightBlue,
                            unfocusedIndicatorColor = mediumGray,
                            cursorColor = brightBlue
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de contraseña
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = brightBlue,
                            unfocusedIndicatorColor = mediumGray,
                            cursorColor = brightBlue
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Enlaces "Crear cuenta" y "Recuperar contraseña"
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextButton(onClick = { navController.navigate("register") }) {
                            Text("¿No tienes una cuenta? Regístrate.", color = brightBlue)
                        }
                        TextButton(onClick = { navController.navigate("recover") }) {
                            Text("¿Olvidó su contraseña?", color = brightBlue)
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón de Acceso
                    Button(
                        onClick = {

                            val doctor = dbHelper.getDoctorByEmail(email.trim())

                            if (doctor != null && doctor.passwordHash == password) {
                                // Éxito: Navegar a Home y pasar el ID
                                Toast.makeText(context, "¡Acceso correcto! Bienvenido, ${doctor.name}", Toast.LENGTH_LONG).show()
                                navController.navigate("home/${doctor.id}") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                Toast.makeText(context, "Credenciales incorrectas o médico no registrado", Toast.LENGTH_LONG).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = brightBlue),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Acceso", fontSize = 18.sp, color = Color.White)
                    }
                }
            }
        }
    }
}



