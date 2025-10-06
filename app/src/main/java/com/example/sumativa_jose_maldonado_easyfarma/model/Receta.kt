package com.example.sumativa_jose_maldonado_easyfarma.model

data class Receta(
    val id: Long = 0,
    val doctorId: Long,
    val patientName: String,
    val patientRut: String,
    val medication: String,
    val dosage: String,
    val instructions: String,
    val date: String
)