package com.example.registrosapp.domain.use_case.empleado

import com.example.registrosapp.data.local.dao.EmpleadoDao
import com.example.registrosapp.data.local.entity.EmpleadoEntity

class ActualizarEmpleadoUseCase(private val empleadoDao: EmpleadoDao) {
    suspend operator fun invoke(empleado: EmpleadoEntity): String {
        if (empleado.FechaIngreso.isBlank() || empleado.Nombres.isBlank() || empleado.Sexo.isBlank()) {
            return "Error: Todos los campos son obligatorios"
        }
        if (empleado.Sueldo <= 0) {
            return "Error: El sueldo debe ser mayor a cero"
        }
        empleadoDao.actualizar(empleado)
        return "¡Empleado actualizado con éxito!"
    }
}