package com.example.sumativa_jose_maldonado_easyfarma.util

import com.example.sumativa_jose_maldonado_easyfarma.model.User

// Función para validar login del usuario
fun validateUserLogin(username: String, password: String, users: List<User>): Boolean {
    // Buscar el usuario y validar que la contraseña coincida
    return users.any { it.username == username && it.password == password }
}

// Función para validar el registro
fun validateRegistration(
    username: String,
    password: String,
    confirmPassword: String,
    users: List<User>
): String? {
    return when {
        username.isBlank() -> "El nombre de usuario no puede estar vacío"
        password.isBlank() -> "La contraseña no puede estar vacía"
        password != confirmPassword -> "Las contraseñas no coinciden"
        users.any { it.username == username } -> "El usuario ya existe"
        users.size >= 5 -> "Número máximo de usuarios alcanzado (5)"
        else -> null
    }
}