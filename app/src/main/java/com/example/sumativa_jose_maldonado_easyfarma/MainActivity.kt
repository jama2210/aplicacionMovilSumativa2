package com.example.sumativa_jose_maldonado_easyfarma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sumativa_jose_maldonado_easyfarma.ui.theme.Sumativa_jose_maldonado_easyFarmaTheme
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sumativa_jose_maldonado_easyfarma.model.User
import com.example.sumativa_jose_maldonado_easyfarma.ui.theme.RegisterScreen
import com.example.sumativa_jose_maldonado_easyfarma.ui.theme.LoginScreen
import com.example.sumativa_jose_maldonado_easyfarma.ui.theme.RecoverPasswordScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()

    // Usuarios ya registrados
    val preRegistered = remember {
        mutableStateListOf(
            User("usuario1", "clave1"),
            User("usuario2", "clave2"),
            User("usuario3", "clave3")
        )
    }

    // Se agregan usuarios dinámicamente
    val users = remember { mutableStateListOf<User>().apply { addAll(preRegistered) } }

    // Navegación
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, users) }
        composable("register") { RegisterScreen(navController, users) }
        composable("recover") { RecoverPasswordScreen(navController, users) }
    }
}