package com.example.registrosapp.domain.use_case.empleado

import com.example.registrosapp.data.local.dao.EmpleadoDao
import com.example.registrosapp.data.local.entity.EmpleadoEntity

class GuardarEmpleadoUseCase(private val empleadoDao: EmpleadoDao) {

    suspend operator fun invoke(
        fechaIngreso: String,
        nombres: String,
        sexo: String,
        ocupacion: String,
        sueldo: Double
    ): String {
        if (fechaIngreso.isBlank() || nombres.isBlank() || sexo.isBlank() || ocupacion.isBlank()) {
            return "Error: Todos los campos son obligatorios"
        }

        if (sueldo <= 0) {
            return "Error: El empleado debe tener un sueldo asignado mayor a cero"
        }

        val existe = empleadoDao.buscarPorNombre(nombres)
        if (existe != null) {
            return "Error: Ya existe un empleado con ese nombre"
        }

        val nuevoEmpleado = EmpleadoEntity(
            FechaIngreso = fechaIngreso,
            Nombres = nombres,
            Sexo = sexo,
            Ocupacion = ocupacion,
            Sueldo = sueldo
        )
        empleadoDao.insertar(nuevoEmpleado)

        return "¡Empleado guardado con éxito!"
    }
}