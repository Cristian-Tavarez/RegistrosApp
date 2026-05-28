package com.example.registrosapp.domain.use_case.ocupacion

import com.example.registrosapp.data.local.dao.OcupacionDao
import com.example.registrosapp.data.local.entity.OcupacionEntity

class EliminarOcupacionUseCase(private val ocupacionDao: OcupacionDao) {
    suspend operator fun invoke(ocupacion: OcupacionEntity) {
        ocupacionDao.eliminar(ocupacion)
    }
}