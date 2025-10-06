package com.example.sumativa_jose_maldonado_easyfarma

import android.os.Bundle
import android.widget.Toast //
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sumativa_jose_maldonado_easyfarma.util.SettingsViewModel
import com.example.sumativa_jose_maldonado_easyfarma.ui.theme.Sumativa_jose_maldonado_easyFarmaTheme
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sumativa_jose_maldonado_easyfarma.model.User
import com.example.sumativa_jose_maldonado_easyfarma.ui.theme.HomeScreen
import com.example.sumativa_jose_maldonado_easyfarma.ui.theme.RegisterScreen
import com.example.sumativa_jose_maldonado_easyfarma.ui.theme.LoginScreen
import com.example.sumativa_jose_maldonado_easyfarma.ui.theme.RecoverPasswordScreen
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import com.example.sumativa_jose_maldonado_easyfarma.ui.theme.CreateRecetaScreen
import androidx.compose.ui.platform.LocalContext //
import com.example.sumativa_jose_maldonado_easyfarma.ui.theme.DeviceSearchScreen
import com.example.sumativa_jose_maldonado_easyfarma.ui.theme.RecetasListScreen
import com.example.sumativa_jose_maldonado_easyfarma.ui.theme.VoiceInputScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp(settingsViewModel: SettingsViewModel = viewModel()) {
    // Obtener los estados del ViewModel
    val isDarkMode = settingsViewModel.isDarkMode
    val fontScaleFactor = settingsViewModel.fontScaleFactor

    // Obtenemos el contexto para usar Toast dentro del @Composable
    val context = LocalContext.current // <-- Referencia al contexto

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

    Sumativa_jose_maldonado_easyFarmaTheme(
        darkTheme = isDarkMode,
        fontScaleFactor = fontScaleFactor
    ) {
        val navController = rememberNavController()

        // Navegación
        NavHost(
            navController = navController,
            startDestination = "login",
            // ANIMACIONES PARA LA TRANSICIÓN GLOBAL DE PANTALLAS
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(500)
                ) + fadeIn(tween(500))
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(500)
                ) + fadeOut(tween(500))
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(500)
                ) + fadeIn(tween(500))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(500)
                ) + fadeOut(tween(500))
            }
        ) {
            // Definición de Composable Screens
            composable("login") {
                LoginScreen(navController, users, settingsViewModel)
            }
            composable("register") {
                RegisterScreen(navController, users)
            }
            composable("recover") {
                RecoverPasswordScreen(navController, users)
            }

            // RUTA HOME
            composable(
                route = "home/{doctorId}"
            ) { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getString("doctorId")?.toLongOrNull() ?: -1L

                HomeScreen(
                    navController = navController,
                    settingsViewModel = settingsViewModel,
                    doctorId = doctorId
                )
            }

            // RUTA CREAR RECETA
            composable(route = "create_receta/{doctorId}") { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getString("doctorId")?.toLongOrNull() ?: -1L

                if (doctorId != -1L) {
                    CreateRecetaScreen(navController = navController, doctorId = doctorId)
                } else {
                    // Usamos la variable 'context' declarada arriba
                    Toast.makeText(context, "Sesión inválida.", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") { popUpTo(0) }
                }
            }

            composable(route = "voice_input/{doctorId}") { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getString("doctorId")?.toLongOrNull() ?: -1L
                if (doctorId != -1L) {
                    VoiceInputScreen(navController = navController, doctorId = doctorId)
                } else {
                    Toast.makeText(LocalContext.current, "Sesión inválida.", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") { popUpTo(0) }
                }
            }

            composable(route = "device_search") {
                // No necesita doctorId
                DeviceSearchScreen(navController = navController)
            }

            composable(route = "recetas_list/{doctorId}") { backStackEntry ->
                val doctorId = backStackEntry.arguments?.getString("doctorId")?.toLongOrNull() ?: -1L
                if (doctorId != -1L) {
                    RecetasListScreen(navController = navController, doctorId = doctorId)
                } else {
                    Toast.makeText(LocalContext.current, "Sesión inválida.", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") { popUpTo(0) }
                }
            }
        }
    }
}