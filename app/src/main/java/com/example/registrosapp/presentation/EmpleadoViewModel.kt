package com.example.registrosapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrosapp.data.EmpleadoDao
import com.example.registrosapp.data.EmpleadoEntity
import kotlinx.coroutines.launch

class EmpleadoViewModel(private val empleadoDao: EmpleadoDao) : ViewModel() {

    var fechaIngreso by mutableStateOf("")
    var nombres by mutableStateOf("")
    var sexo by mutableStateOf("")
    var sueldo by mutableStateOf("")
    var mensajeError by mutableStateOf("")

    fun guardar() {
        if (fechaIngreso.isBlank() || nombres.isBlank() || sexo.isBlank() || sueldo.isBlank()) {
            mensajeError = "Error: Todos los campos son obligatorios"
            return
        }

        val sueldoDouble = sueldo.toDoubleOrNull()
        if (sueldoDouble == null) {
            mensajeError = "Error: El sueldo debe ser un número válido"
            return
        }

        viewModelScope.launch {
            try {
                val existe = empleadoDao.buscarPorNombre(nombres)
                if (existe != null) {
                    mensajeError = "Error: Ya existe un empleado con ese nombre"
                    return@launch
                }

                val nuevoEmpleado = EmpleadoEntity(
                    FechaIngreso = fechaIngreso,
                    Nombres = nombres,
                    Sexo = sexo,
                    Sueldo = sueldoDouble
                )
                empleadoDao.insertar(nuevoEmpleado)
                mensajeError = "¡Empleado guardado con éxito!"

                fechaIngreso = ""
                nombres = ""
                sexo = ""
                sueldo = ""

            } catch (e: Exception) {
                mensajeError = "Error al guardar: ${e.message}"
            }
        }
    }
}