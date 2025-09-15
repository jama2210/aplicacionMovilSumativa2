// Archivo: app/src/main/java/com/yourcompany/easyreceta/util/extensions.kt

package com.example.sumativa_jose_maldonado_easyfarma.util

import com.example.sumativa_jose_maldonado_easyfarma.model.User
import java.lang.Exception

// Función de extensión para validar contraseña
inline fun User.isPasswordValid(input: String): Boolean = this.password == input

// Propiedad de extensión para iniciales
val User.initials: String
    get() = username.take(2).uppercase()

// Función inline de validación, con crossinline para evitar retornos no locales
inline fun validateField(value: String, message: String, crossinline onValid: () -> Unit) {
    if (value.isBlank()) {
        throw Exception(message)
    } else {
        onValid()
    }
}