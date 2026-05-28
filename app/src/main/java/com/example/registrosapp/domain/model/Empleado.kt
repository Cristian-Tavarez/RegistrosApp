package com.example.registrosapp.domain.model

data class Empleado(
    val empleadoId: Int = 0,
    val fechaIngreso: String,
    val nombres: String,
    val sexo: String,
    val ocupacion: String,
    val sueldo: Double
)