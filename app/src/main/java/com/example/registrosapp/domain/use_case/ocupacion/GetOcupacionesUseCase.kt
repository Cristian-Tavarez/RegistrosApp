package com.example.registrosapp.domain.use_case.ocupacion

import com.example.registrosapp.data.local.dao.OcupacionDao
import com.example.registrosapp.data.local.entity.OcupacionEntity
import kotlinx.coroutines.flow.Flow

class GetOcupacionesUseCase(private val ocupacionDao: OcupacionDao) {
    operator fun invoke(): Flow<List<OcupacionEntity>> {
        return ocupacionDao.obtenerTodos()
    }
}