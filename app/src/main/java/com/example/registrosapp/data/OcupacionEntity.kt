package com.example.registrosapp.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Ocupaciones",
    indices = [Index(value = ["Descripcion"], unique = true)]
)
data class OcupacionEntity(
    @PrimaryKey(autoGenerate = true)
    val OcupacionId: Int = 0,
    val Descripcion: String,
    val Sueldo: Double
)