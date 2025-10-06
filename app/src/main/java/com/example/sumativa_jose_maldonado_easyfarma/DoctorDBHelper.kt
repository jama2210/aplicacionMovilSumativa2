package com.example.sumativa_jose_maldonado_easyfarma

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sumativa_jose_maldonado_easyfarma.model.Doctor
import com.example.sumativa_jose_maldonado_easyfarma.model.Receta

class DoctorDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "EasyRecetaDB"
        private const val TABLE_DOCTORS = "doctors"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_LAST_NAME = "lastName"
        private const val KEY_RUT = "rut"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD_HASH = "passwordHash"
        private const val KEY_SPECIALIZATION = "specialization"


        private const val TABLE_RECETAS = "recetas"
        private const val KEY_R_ID = "id"
        private const val KEY_R_DOCTOR_ID = "doctorId"
        private const val KEY_R_PATIENT_NAME = "patientName"
        private const val KEY_R_PATIENT_RUT = "patientRut"
        private const val KEY_R_MEDICATION = "medication"
        private const val KEY_R_DOSAGE = "dosage"
        private const val KEY_R_INSTRUCTIONS = "instructions"
        private const val KEY_R_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_DOCTORS_TABLE = ("CREATE TABLE $TABLE_DOCTORS("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_NAME TEXT,"
                + "$KEY_LAST_NAME TEXT,"
                + "$KEY_RUT TEXT UNIQUE,"
                + "$KEY_EMAIL TEXT UNIQUE,"
                + "$KEY_PASSWORD_HASH TEXT,"
                + "$KEY_SPECIALIZATION TEXT)")
        db.execSQL(CREATE_DOCTORS_TABLE)

        val CREATE_RECETAS_TABLE = ("CREATE TABLE $TABLE_RECETAS("
                + "$KEY_R_ID INTEGER PRIMARY KEY,"
                + "$KEY_R_DOCTOR_ID INTEGER," // Clave foránea al médico
                + "$KEY_R_PATIENT_NAME TEXT,"
                + "$KEY_R_PATIENT_RUT TEXT,"
                + "$KEY_R_MEDICATION TEXT,"
                + "$KEY_R_DOSAGE TEXT,"
                + "$KEY_R_INSTRUCTIONS TEXT,"
                + "$KEY_R_DATE TEXT)")
        db.execSQL(CREATE_RECETAS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DOCTORS")
        onCreate(db)
    }

    // --- OPERACIONES CRUD ---

    fun addDoctor(doctor: Doctor): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NAME, doctor.name)
            put(KEY_LAST_NAME, doctor.lastName)
            put(KEY_RUT, doctor.rut)
            put(KEY_EMAIL, doctor.email)
            put(KEY_PASSWORD_HASH, doctor.passwordHash)
            put(KEY_SPECIALIZATION, doctor.specialization)
        }
        val id = db.insert(TABLE_DOCTORS, null, values)
        db.close()
        return id
    }

    fun getDoctorByEmail(email: String): Doctor? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_DOCTORS,
            arrayOf(KEY_ID, KEY_NAME, KEY_LAST_NAME, KEY_RUT, KEY_EMAIL, KEY_PASSWORD_HASH, KEY_SPECIALIZATION),
            "$KEY_EMAIL=?",
            arrayOf(email),
            null, null, null, null
        )

        var doctor: Doctor? = null
        cursor?.use {
            if (it.moveToFirst()) {
                doctor = Doctor(
                    id = it.getLong(it.getColumnIndexOrThrow(KEY_ID)),
                    name = it.getString(it.getColumnIndexOrThrow(KEY_NAME)),
                    lastName = it.getString(it.getColumnIndexOrThrow(KEY_LAST_NAME)),
                    rut = it.getString(it.getColumnIndexOrThrow(KEY_RUT)),
                    email = it.getString(it.getColumnIndexOrThrow(KEY_EMAIL)),
                    passwordHash = it.getString(it.getColumnIndexOrThrow(KEY_PASSWORD_HASH)),
                    specialization = it.getString(it.getColumnIndexOrThrow(KEY_SPECIALIZATION))
                )
            }
        }
        return doctor
    }

    fun updateDoctor(doctor: Doctor): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NAME, doctor.name)
            put(KEY_LAST_NAME, doctor.lastName)
            put(KEY_RUT, doctor.rut)
            put(KEY_EMAIL, doctor.email)
            put(KEY_PASSWORD_HASH, doctor.passwordHash)
            put(KEY_SPECIALIZATION, doctor.specialization)
        }
        val success = db.update(TABLE_DOCTORS, values, "$KEY_ID=?", arrayOf(doctor.id.toString()))
        db.close()
        return success
    }

    fun deleteDoctor(id: Long): Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_DOCTORS, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
        return success
    }

    fun isEmailRegistered(email: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $KEY_ID FROM $TABLE_DOCTORS WHERE $KEY_EMAIL=?", arrayOf(email))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun getDoctorById(id: Long): Doctor? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_DOCTORS,
            arrayOf(KEY_ID, KEY_NAME, KEY_LAST_NAME, KEY_RUT, KEY_EMAIL, KEY_PASSWORD_HASH, KEY_SPECIALIZATION),
            "$KEY_ID=?",
            arrayOf(id.toString()),
            null, null, null, null
        )

        var doctor: Doctor? = null
        cursor?.use {
            if (it.moveToFirst()) {
                doctor = Doctor(
                    id = it.getLong(it.getColumnIndexOrThrow(KEY_ID)),
                    name = it.getString(it.getColumnIndexOrThrow(KEY_NAME)),
                    lastName = it.getString(it.getColumnIndexOrThrow(KEY_LAST_NAME)),
                    rut = it.getString(it.getColumnIndexOrThrow(KEY_RUT)),
                    email = it.getString(it.getColumnIndexOrThrow(KEY_EMAIL)),
                    passwordHash = it.getString(it.getColumnIndexOrThrow(KEY_PASSWORD_HASH)),
                    specialization = it.getString(it.getColumnIndexOrThrow(KEY_SPECIALIZATION))
                )
            }
        }
        return doctor
    }

    fun addReceta(receta: Receta): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_R_DOCTOR_ID, receta.doctorId)
            put(KEY_R_PATIENT_NAME, receta.patientName)
            put(KEY_R_PATIENT_RUT, receta.patientRut)
            put(KEY_R_MEDICATION, receta.medication)
            put(KEY_R_DOSAGE, receta.dosage)
            put(KEY_R_INSTRUCTIONS, receta.instructions)
            put(KEY_R_DATE, receta.date)
        }
        val id = db.insert(TABLE_RECETAS, null, values)
        db.close()
        return id
    }

    fun getAllRecetasByDoctor(doctorId: Long): List<Receta> {
        val recetaList = mutableListOf<Receta>()
        val selectQuery = "SELECT * FROM $TABLE_RECETAS WHERE $KEY_R_DOCTOR_ID = $doctorId ORDER BY $KEY_R_ID DESC"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val receta = Receta(
                        id = cursor.getLong(cursor.getColumnIndexOrThrow(KEY_R_ID)),
                        doctorId = cursor.getLong(cursor.getColumnIndexOrThrow(KEY_R_DOCTOR_ID)),
                        patientName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_R_PATIENT_NAME)),
                        patientRut = cursor.getString(cursor.getColumnIndexOrThrow(KEY_R_PATIENT_RUT)),
                        medication = cursor.getString(cursor.getColumnIndexOrThrow(KEY_R_MEDICATION)),
                        dosage = cursor.getString(cursor.getColumnIndexOrThrow(KEY_R_DOSAGE)),
                        instructions = cursor.getString(cursor.getColumnIndexOrThrow(KEY_R_INSTRUCTIONS)),
                        date = cursor.getString(cursor.getColumnIndexOrThrow(KEY_R_DATE))
                    )
                    recetaList.add(receta)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            // Manejo básico de errores
            e.printStackTrace()
        } finally {
            cursor?.close()
            db.close()
        }
        return recetaList
    }
}