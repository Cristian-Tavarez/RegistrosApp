package com.example.registrosapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrosapp.data.local.entity.EmpleadoEntity
import com.example.registrosapp.domain.use_case.empleado.ActualizarEmpleadoUseCase
import com.example.registrosapp.domain.use_case.empleado.EliminarEmpleadoUseCase
import com.example.registrosapp.domain.use_case.empleado.GetEmpleadosUseCase
import com.example.registrosapp.domain.use_case.empleado.GuardarEmpleadoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmpleadoViewModel @Inject constructor(
    private val getEmpleadosUseCase: GetEmpleadosUseCase,
    private val guardarEmpleadoUseCase: GuardarEmpleadoUseCase,
    private val actualizarEmpleadoUseCase: ActualizarEmpleadoUseCase,
    private val eliminarEmpleadoUseCase: EliminarEmpleadoUseCase
) : ViewModel() {

    val empleadosList: Flow<List<EmpleadoEntity>> = getEmpleadosUseCase()

    var fechaIngreso by mutableStateOf("")
    var nombres by mutableStateOf("")
    var sexo by mutableStateOf("")
    var ocupacionSeleccionada by mutableStateOf("")
    var sueldoCalculado by mutableStateOf(0.0)
    var mensajeError by mutableStateOf("")

    var empleadoAEditar by mutableStateOf<EmpleadoEntity?>(null)

    fun guardar() {
        viewModelScope.launch {
            if (empleadoAEditar == null) {
                // MODO CREAR
                mensajeError = guardarEmpleadoUseCase(
                    fechaIngreso,
                    nombres,
                    sexo,
                    ocupacionSeleccionada,
                    sueldoCalculado
                )
            } else {
                val empleadoActualizado = EmpleadoEntity(
                    EmpleadoId = empleadoAEditar!!.EmpleadoId,
                    FechaIngreso = fechaIngreso,
                    Nombres = nombres,
                    Sexo = sexo,
                    Ocupacion = ocupacionSeleccionada,
                    Sueldo = sueldoCalculado
                )
                mensajeError = actualizarEmpleadoUseCase(empleadoActualizado)
            }

            if (mensajeError.startsWith("¡")) {
                limpiarFormulario()
            }
        }
    }

    fun eliminarEmpleado(empleado: EmpleadoEntity) {
        viewModelScope.launch {
            eliminarEmpleadoUseCase(empleado)
            if (empleadoAEditar?.EmpleadoId == empleado.EmpleadoId) {
                limpiarFormulario()
            }
        }
    }

    fun prepararEdicion(empleado: EmpleadoEntity) {
        empleadoAEditar = empleado
        fechaIngreso = empleado.FechaIngreso
        nombres = empleado.Nombres
        sexo = empleado.Sexo
        ocupacionSeleccionada = empleado.Ocupacion
        sueldoCalculado = empleado.Sueldo
        mensajeError = ""
    }

    fun limpiarFormulario() {
        fechaIngreso = ""
        nombres = ""
        sexo = ""
        ocupacionSeleccionada = ""
        sueldoCalculado = 0.0
        empleadoAEditar = null
        mensajeError = ""
    }
}