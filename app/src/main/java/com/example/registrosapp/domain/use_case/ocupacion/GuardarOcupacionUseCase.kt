package com.example.registrosapp.domain.use_case.ocupacion

import com.example.registrosapp.data.local.dao.OcupacionDao
import com.example.registrosapp.data.local.entity.OcupacionEntity

class GuardarOcupacionUseCase(private val ocupacionDao: OcupacionDao) {
    suspend operator fun invoke(descripcion: String, sueldoText: String): String {
        if (descripcion.isBlank() || sueldoText.isBlank()) {
            return "Error: Todos los campos son obligatorios"
        }

        val sueldoDouble = sueldoText.toDoubleOrNull()
        if (sueldoDouble == null || sueldoDouble <= 0) {
            return "Error: El sueldo debe ser un número mayor a 0"
        }

        val existe = ocupacionDao.buscarPorDescripcion(descripcion)
        if (existe != null) {
            return "Error: Ya existe una ocupación con esa descripción"
        }

        val nuevaOcupacion = OcupacionEntity(Descripcion = descripcion, Sueldo = sueldoDouble)
        ocupacionDao.insertar(nuevaOcupacion)
        return "¡Ocupación guardada con éxito!"
    }
}