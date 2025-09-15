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


// Colores de la app EasyFarma
val navyBlue = Color(0xFF00154F)
val brightBlue = Color(0xFF013EE9)
val lightGray = Color(0xFFF0F0F0)
val mediumGray = Color(0xFFE0E0E0) // Un gris un poco más oscuro para los inputs


@Composable
fun LoginScreen(navController: NavController, users: List<User>) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray)
            .padding(horizontal = 24.dp), // Aplicamos padding horizontal al Column
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo y texto
        Image(
            painter = painterResource(id = R.drawable.logo_easy_receta), // Asegúrate de tener esta imagen en la carpeta drawable
            contentDescription = "Logo Easy Receta",
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = "Iniciar sesión",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = navyBlue,
            modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
        )

        // Campo de correo electrónico con el diseño del input de la imagen
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

        // Campo de contraseña con el diseño del input de la imagen
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
            horizontalAlignment = Alignment.Start // Alinear los textos a la izquierda
        ) {
            TextButton(onClick = { navController.navigate("register") }) {
                Text("¿No tienes una cuenta? Crea una.", color = brightBlue)
            }
            TextButton(onClick = { navController.navigate("recover") }) {
                Text("¿Olvidó su contraseña?", color = brightBlue)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Acceso
        Button(
            onClick = {
                if (validateUserLogin(email, password, users)) {
                    Toast.makeText(context, "¡Acceso correcto!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
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



