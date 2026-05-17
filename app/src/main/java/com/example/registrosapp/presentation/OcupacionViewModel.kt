package com.example.registrosapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrosapp.data.OcupacionDao
import com.example.registrosapp.data.OcupacionEntity
import kotlinx.coroutines.launch

class OcupacionViewModel(private val ocupacionDao: OcupacionDao) : ViewModel() {

    var descripcion by mutableStateOf("")
    var sueldo by mutableStateOf("")
    var mensajeError by mutableStateOf("")

    fun guardar() {

        if (descripcion.isBlank() || sueldo.isBlank()) {
            mensajeError = "Error: Todos los campos son obligatorios."
            return
        }

        val sueldoDouble = sueldo.toDoubleOrNull()
        if (sueldoDouble == null || sueldoDouble <= 0) {
            mensajeError = "Error: Ingrese un sueldo válido mayor a 0."
            return
        }

        viewModelScope.launch {
            val existe = ocupacionDao.buscarPorDescripcion(descripcion)

            if (existe != null) {
                mensajeError = "Error: Ya existe una ocupación con esta descripción."
            } else {
                val nuevaOcupacion = OcupacionEntity(
                    Descripcion = descripcion,
                    Sueldo = sueldoDouble
                )
                ocupacionDao.insertar(nuevaOcupacion)

                descripcion = ""
                sueldo = ""
                mensajeError = "¡Guardado con éxito!"
            }
        }
    }
}