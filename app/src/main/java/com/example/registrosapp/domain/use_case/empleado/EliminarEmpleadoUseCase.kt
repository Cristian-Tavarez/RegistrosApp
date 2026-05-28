package com.example.registrosapp.domain.use_case.empleado

import com.example.registrosapp.data.local.dao.EmpleadoDao
import com.example.registrosapp.data.local.entity.EmpleadoEntity

class EliminarEmpleadoUseCase(private val empleadoDao: EmpleadoDao) {
    suspend operator fun invoke(empleado: EmpleadoEntity) {
        empleadoDao.eliminar(empleado)
    }
}