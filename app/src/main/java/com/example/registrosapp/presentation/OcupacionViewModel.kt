package com.example.registrosapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registrosapp.data.local.entity.OcupacionEntity
import com.example.registrosapp.domain.use_case.ocupacion.ActualizarOcupacionUseCase
import com.example.registrosapp.domain.use_case.ocupacion.EliminarOcupacionUseCase
import com.example.registrosapp.domain.use_case.ocupacion.GetOcupacionesUseCase
import com.example.registrosapp.domain.use_case.ocupacion.GuardarOcupacionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OcupacionViewModel @Inject constructor(
    private val getOcupacionesUseCase: GetOcupacionesUseCase,
    private val guardarOcupacionUseCase: GuardarOcupacionUseCase,
    private val actualizarOcupacionUseCase: ActualizarOcupacionUseCase,
    private val eliminarOcupacionUseCase: EliminarOcupacionUseCase
) : ViewModel() {

    val ocupacionesList: Flow<List<OcupacionEntity>> = getOcupacionesUseCase()

    var descripcion by mutableStateOf("")
    var sueldo by mutableStateOf("")
    var mensajeError by mutableStateOf("")

    var ocupacionAEditar by mutableStateOf<OcupacionEntity?>(null)

    fun guardar() {
        viewModelScope.launch {
            if (ocupacionAEditar == null) {
                mensajeError = guardarOcupacionUseCase(descripcion, sueldo)
            } else {
                mensajeError = actualizarOcupacionUseCase(
                    ocupacionId = ocupacionAEditar!!.OcupacionId,
                    descripcion = descripcion,
                    sueldoText = sueldo
                )
            }

            if (mensajeError.startsWith("¡")) {
                limpiarFormulario()
            }
        }
    }

    fun eliminarOcupacion(ocupacion: OcupacionEntity) {
        viewModelScope.launch {
            eliminarOcupacionUseCase(ocupacion)
            if (ocupacionAEditar?.OcupacionId == ocupacion.OcupacionId) {
                limpiarFormulario()
            }
        }
    }

    fun prepararEdicion(ocupacion: OcupacionEntity) {
        ocupacionAEditar = ocupacion
        descripcion = ocupacion.Descripcion
        sueldo = ocupacion.Sueldo.toString()
        mensajeError = ""
    }

    fun limpiarFormulario() {
        descripcion = ""
        sueldo = ""
        ocupacionAEditar = null
        mensajeError = ""
    }
}