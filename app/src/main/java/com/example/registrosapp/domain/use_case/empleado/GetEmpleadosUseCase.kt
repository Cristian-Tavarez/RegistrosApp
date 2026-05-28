package com.example.registrosapp.domain.use_case.empleado

import com.example.registrosapp.data.local.dao.EmpleadoDao
import com.example.registrosapp.data.local.entity.EmpleadoEntity
import kotlinx.coroutines.flow.Flow

class GetEmpleadosUseCase(private val empleadoDao: EmpleadoDao) {
    operator fun invoke(): Flow<List<EmpleadoEntity>> {
        return empleadoDao.obtenerTodos()
    }
}