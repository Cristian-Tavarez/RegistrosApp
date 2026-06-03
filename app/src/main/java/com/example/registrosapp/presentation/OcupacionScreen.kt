package com.example.registrosapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.registrosapp.data.local.entity.OcupacionEntity

@Composable
fun OcupacionScreen(viewModel: OcupacionViewModel, esTablet: Boolean = false) {
    val listaOcupaciones by viewModel.ocupacionesList.collectAsState(initial = emptyList())

    if (esTablet) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (viewModel.ocupacionAEditar == null) "Registro de Ocupaciones" else "Modificar Ocupación",
                    style = MaterialTheme.typography.headlineSmall
                )

                OutlinedTextField(
                    value = viewModel.descripcion,
                    onValueChange = { viewModel.descripcion = it },
                    label = { Text("Descripción de la Ocupación") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.sueldo,
                    onValueChange = { viewModel.sueldo = it },
                    label = { Text("Sueldo Mensual (RD$)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                if (viewModel.mensajeError.isNotEmpty()) {
                    Text(
                        text = viewModel.mensajeError,
                        color = if (viewModel.mensajeError.startsWith("Error")) Color.Red else Color(0xFF007F00),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = { viewModel.guardar() }, modifier = Modifier.weight(1f)) {
                        Text(if (viewModel.ocupacionAEditar == null) "Guardar" else "Actualizar")
                    }

                    if (viewModel.ocupacionAEditar != null) {
                        OutlinedButton(onClick = { viewModel.limpiarFormulario() }, modifier = Modifier.weight(1f)) {
                            Text("Cancelar")
                        }
                    }
                }
            }

            VerticalDivider(modifier = Modifier.fillMaxHeight(), color = MaterialTheme.colorScheme.outlineVariant)

            Column(
                modifier = Modifier
                    .weight(1.2f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = "Ocupaciones Registradas",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ListaOcupacionesComponent(
                    listaOcupaciones = listaOcupaciones,
                    viewModel = viewModel
                )
            }
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = if (viewModel.ocupacionAEditar == null) "Registro de Ocupaciones" else "Modificar Ocupación",
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = viewModel.descripcion,
                onValueChange = { viewModel.descripcion = it },
                label = { Text("Descripción de la Ocupación") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.sueldo,
                onValueChange = { viewModel.sueldo = it },
                label = { Text("Sueldo Mensual (RD$)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            if (viewModel.mensajeError.isNotEmpty()) {
                Text(
                    text = viewModel.mensajeError,
                    color = if (viewModel.mensajeError.startsWith("Error")) Color.Red else Color(0xFF007F00),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { viewModel.guardar() }, modifier = Modifier.weight(1f)) {
                    Text(if (viewModel.ocupacionAEditar == null) "Guardar" else "Actualizar")
                }

                if (viewModel.ocupacionAEditar != null) {
                    OutlinedButton(onClick = { viewModel.limpiarFormulario() }, modifier = Modifier.weight(1f)) {
                        Text("Cancelar")
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text(text = "Ocupaciones Registradas", style = MaterialTheme.typography.titleMedium)

            Box(modifier = Modifier.weight(1f)) {
                ListaOcupacionesComponent(
                    listaOcupaciones = listaOcupaciones,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun ListaOcupacionesComponent(
    listaOcupaciones: List<OcupacionEntity>,
    viewModel: OcupacionViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (listaOcupaciones.isEmpty()) {
            item {
                Text(
                    text = "No hay ocupaciones registradas.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }

        items(listaOcupaciones) { ocupacion ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = ocupacion.Descripcion, style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = "RD$ ${String.format("%.2f", ocupacion.Sueldo)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Row {
                        IconButton(onClick = { viewModel.prepararEdicion(ocupacion) }) {
                            Icon(Icons.Default.Edit, "Editar", tint = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { viewModel.eliminarOcupacion(ocupacion) }) {
                            Icon(Icons.Default.Delete, "Eliminar", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}