package com.example.registrosapp.domain.use_case.horas_extras

import com.example.registrosapp.data.local.dao.HoraExtraDao
import com.example.registrosapp.data.local.entity.HoraExtraEntity

class EliminarHoraExtraUseCase(private val horaExtraDao: HoraExtraDao) {
    suspend operator fun invoke(horaExtra: HoraExtraEntity) {
        horaExtraDao.eliminar(horaExtra)
    }
}