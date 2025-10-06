package com.example.sumativa_jose_maldonado_easyfarma.model

data class Doctor(
    val id: Long = 0,
    val name: String,
    val lastName: String,
    val rut: String,
    val email: String,
    val passwordHash: String,
    val specialization: String
)