package com.example.registrosapp.domain.use_case.horas_extras

import com.example.registrosapp.domain.model.HoraExtraResult

class CalcularHoraExtraUseCase {

    operator fun invoke(sueldoBase: Double, totalHorasExtras: Double, esNocturna: Boolean): HoraExtraResult {
        val divisorSueldoDiario = 23.53

        val sueldoPorDia = sueldoBase / divisorSueldoDiario
        val sueldoPorHoraNormal = sueldoPorDia / 8

        val horasAl35 = if (totalHorasExtras > 24.0) 24.0 else totalHorasExtras
        val horasAl100 = if (totalHorasExtras > 24.0) totalHorasExtras - 24.0 else 0.0

        val factorNocturno = if (esNocturna) 1.15 else 1.0

        val precioHora35 = sueldoPorHoraNormal * 1.35 * factorNocturno
        val precioHora100 = sueldoPorHoraNormal * 2.00 * factorNocturno

        val monto35 = horasAl35 * precioHora35
        val monto100 = horasAl100 * precioHora100
        val totalAPagar = monto35 + monto100

        return HoraExtraResult(
            horasAl35 = horasAl35,
            horasAl100 = horasAl100,
            monto35 = monto35,
            monto100 = monto100,
            totalAPagar = totalAPagar
        )
    }
}