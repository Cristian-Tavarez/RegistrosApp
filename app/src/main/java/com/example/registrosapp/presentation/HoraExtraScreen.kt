package com.example.registrosapp.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.registrosapp.data.local.entity.EmpleadoEntity

@Composable
fun HoraExtraScreen(listaEmpleados: List<EmpleadoEntity>, horaExtraViewModel: HoraExtraViewModel) {
    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }
    var empleadoSeleccionado by remember { mutableStateOf<EmpleadoEntity?>(null) }
    var horasIntroducidasText by remember { mutableStateOf("") }
    var esNocturna by remember { mutableStateOf(false) }

    val sueldoBase = empleadoSeleccionado?.Sueldo ?: 0.0

    val sueldoPorDia = sueldoBase / 23.53
    val sueldoPorHoraNormal = sueldoPorDia / 8

    val totalHorasExtras = horasIntroducidasText.toDoubleOrNull() ?: 0.0

    val horasAl35 = if (totalHorasExtras > 24.0) 24.0 else totalHorasExtras
    val horasAl100 = if (totalHorasExtras > 24.0) totalHorasExtras - 24.0 else 0.0

    val factorNocturno = if (esNocturna) 1.15 else 1.0

    val precioHora35 = sueldoPorHoraNormal * 1.35 * factorNocturno
    val precioHora100 = sueldoPorHoraNormal * 2.00 * factorNocturno

    val monto35 = horasAl35 * precioHora35
    val monto100 = horasAl100 * precioHora100
    val totalAPagar = monto35 + monto100

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Registro de Horas Extras",
            style = MaterialTheme.typography.headlineSmall
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = empleadoSeleccionado?.let { "${it.Nombres} (RD$ ${it.Sueldo})" } ?: "Seleccione un Empleado",
                onValueChange = {},
                readOnly = true,
                label = { Text("Empleado") },
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        Modifier.clickable { expanded = true }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Box(modifier = Modifier.matchParentSize().clickable { expanded = true })

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                if (listaEmpleados.isEmpty()) {
                    DropdownMenuItem(
                        text = { Text("No hay empleados en la base de datos") },
                        onClick = { expanded = false }
                    )
                } else {
                    listaEmpleados.forEach { empleado ->
                        DropdownMenuItem(
                            text = { Text("${empleado.Nombres} - RD$ ${empleado.Sueldo}") },
                            onClick = {
                                empleadoSeleccionado = empleado
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        if (empleadoSeleccionado != null) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Sueldo Diario Calculado: RD$ ${String.format("%.2f", sueldoPorDia)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        OutlinedTextField(
            value = horasIntroducidasText,
            onValueChange = { horasIntroducidasText = it },
            label = { Text("Cantidad de horas extras semanales") },
            placeholder = { Text("Ej: 30") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            enabled = empleadoSeleccionado != null
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Horario Nocturno (+15% extra)")
            Switch(
                checked = esNocturna,
                onCheckedChange = { esNocturna = it },
                enabled = empleadoSeleccionado != null
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "DESGLOSE DE PAGO",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Valor Hora Normal:")
                    Text("RD$ ${String.format("%.2f", sueldoPorHoraNormal)}")
                }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Horas al 35% (${String.format("%.1f", horasAl35)}h):")
                    Text("RD$ ${String.format("%.2f", monto35)}")
                }

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Horas al 100% (${String.format("%.1f", horasAl100)}h):")
                    Text("RD$ ${String.format("%.2f", monto100)}")
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f))

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("TOTAL A PAGAR:", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "RD$ ${String.format("%.2f", totalAPagar)}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Button(
            onClick = {
                empleadoSeleccionado?.let { empleado ->
                    horaExtraViewModel.guardarHoraExtra(
                        empleadoId = empleado.EmpleadoId,
                        nombre = empleado.Nombres,
                        horas = totalHorasExtras,
                        esNocturna = esNocturna,
                        total = totalAPagar,
                        onExito = {
                            horasIntroducidasText = ""
                            esNocturna = false
                            Toast.makeText(context, "¡Horas Extras guardadas con éxito!", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = empleadoSeleccionado != null && totalHorasExtras > 0
        ) {
            Text("Guardar Registro")
        }
    }
}