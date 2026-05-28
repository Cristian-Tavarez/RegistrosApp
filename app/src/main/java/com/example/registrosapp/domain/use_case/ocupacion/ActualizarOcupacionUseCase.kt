package com.example.registrosapp.domain.use_case.ocupacion

import com.example.registrosapp.data.local.dao.OcupacionDao
import com.example.registrosapp.data.local.entity.OcupacionEntity

class ActualizarOcupacionUseCase(private val ocupacionDao: OcupacionDao) {
    suspend operator fun invoke(ocupacionId: Int, descripcion: String, sueldoText: String): String {
        if (descripcion.isBlank() || sueldoText.isBlank()) {
            return "Error: Todos los campos son obligatorios"
        }

        val sueldoDouble = sueldoText.toDoubleOrNull()
        if (sueldoDouble == null || sueldoDouble <= 0) {
            return "Error: El sueldo debe ser un número mayor a 0"
        }

        val ocupacionActualizada = OcupacionEntity(
            OcupacionId = ocupacionId,
            Descripcion = descripcion,
            Sueldo = sueldoDouble
        )

        ocupacionDao.actualizar(ocupacionActualizada)
        return "¡Ocupación actualizada con éxito!"
    }
}