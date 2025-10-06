// Archivo: package com.example.sumativa_jose_maldonado_easyfarma.ui.theme/RecoverPasswordScreen.kt

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sumativa_jose_maldonado_easyfarma.model.User
import com.example.sumativa_jose_maldonado_easyfarma.R
// --- IMPORTACIÓN FALTANTE: SOLUCIÓN PARA 'Brush' ---
import androidx.compose.ui.graphics.Brush






@Composable
fun RecoverPasswordScreen(navController: NavController, users: List<User>) {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(color1, color2, color3)
                )
            )
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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
                    text = "¿Olvidaste tu contraseña?",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
                )
                Text(
                    text = "Ingresa tu correo electrónico para recuperar tu cuenta.",
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 24.dp),
                    style = MaterialTheme.typography.bodyLarge
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
                Spacer(modifier = Modifier.height(24.dp))

                // Botón de Recuperar Contraseña
                Button(
                    onClick = {
                        if (users.any { it.username == email }) {
                            Toast.makeText(context, "Se ha enviado un enlace de recuperación a tu correo.", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "El correo electrónico no está registrado.", Toast.LENGTH_LONG).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = brightBlue),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Recuperar Contraseña", fontSize = 18.sp, color = Color.White)
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