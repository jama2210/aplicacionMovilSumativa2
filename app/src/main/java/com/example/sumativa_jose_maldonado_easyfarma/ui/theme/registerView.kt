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

@Composable
fun RegisterScreen(navController: NavController, users: MutableList<User>) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo y texto
        Image(
            painter = painterResource(id = R.drawable.logo_easy_receta),
            contentDescription = "Logo Easy Receta",
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = "Registro de Usuario",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = navyBlue,
            modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
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
        Spacer(modifier = Modifier.height(16.dp))

        // Campo de confirmar contraseña
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña") },
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
        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Registro
        Button(
            onClick = {
                val validationResult = validateRegistration(email, password, confirmPassword, users)
                if (validationResult == null) {
                    users.add(User(email, password))
                    Toast.makeText(context, "¡Usuario registrado correctamente!", Toast.LENGTH_LONG).show()
                    navController.navigate("login")
                } else {
                    Toast.makeText(context, validationResult, Toast.LENGTH_LONG).show()
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