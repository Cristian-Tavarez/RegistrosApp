package com.example.registrosapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "HorasExtras")
data class HoraExtraEntity(
    @PrimaryKey(autoGenerate = true)
    val HoraExtraId: Int = 0,
    val EmpleadoId: Int,
    val EmpleadoNombre: String,
    val CantidadHoras: Double,
    val EsNocturna: Boolean,
    val TotalPagado: Double
)