package com.example.registrosapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Empleados")
data class EmpleadoEntity(
    @PrimaryKey(autoGenerate = true)
    val EmpleadoId: Int = 0,
    val FechaIngreso: String,
    val Nombres: String,
    val Sexo: String,
    val Sueldo: Double
)