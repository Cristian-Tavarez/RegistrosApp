package com.example.registrosapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrosapp.data.local.dao.HoraExtraDao
import com.example.registrosapp.data.local.entity.HoraExtraEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoraExtraViewModel @Inject constructor (private val horaExtraDao: HoraExtraDao) : ViewModel() {

    fun guardarHoraExtra(
        empleadoId: Int,
        nombre: String,
        horas: Double,
        esNocturna: Boolean,
        total: Double,
        onExito: () -> Unit
    ) {
        viewModelScope.launch {
            val nuevoRegistro = HoraExtraEntity(
                EmpleadoId = empleadoId,
                EmpleadoNombre = nombre,
                CantidadHoras = horas,
                EsNocturna = esNocturna,
                TotalPagado = total
            )
            horaExtraDao.insertar(nuevoRegistro)
            onExito()
        }
    }
}