package com.example.registrosapp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.registrosapp.data.local.entity.EmpleadoEntity
import com.example.registrosapp.data.local.entity.OcupacionEntity

@Composable
fun EmpleadoScreen(
    viewModel: EmpleadoViewModel,
    listaOcupaciones: List<OcupacionEntity>,
    esTablet: Boolean = false
) {
    val listaEmpleados by viewModel.empleadosList.collectAsState(initial = emptyList())
    var dropdownOcupacionExpanded by remember { mutableStateOf(false) }
    var dropdownSexoExpanded by remember { mutableStateOf(false) }

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
                    text = if (viewModel.empleadoAEditar == null) "Registro de Empleados" else "Modificar Empleado",
                    style = MaterialTheme.typography.headlineSmall
                )

                OutlinedTextField(
                    value = viewModel.nombres,
                    onValueChange = { viewModel.nombres = it },
                    label = { Text("Nombres Completos") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = viewModel.fechaIngreso,
                    onValueChange = { viewModel.fechaIngreso = it },
                    label = { Text("Fecha de Ingreso (Ej: 28/05/2026)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = if (viewModel.sexo.isEmpty()) "Seleccione el Sexo" else viewModel.sexo,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Sexo") },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, Modifier.clickable { dropdownSexoExpanded = true }) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Box(modifier = Modifier.matchParentSize().clickable { dropdownSexoExpanded = true })
                    DropdownMenu(expanded = dropdownSexoExpanded, onDismissRequest = { dropdownSexoExpanded = false }) {
                        listOf("Masculino", "Femenino").forEach { opcion ->
                            DropdownMenuItem(text = { Text(opcion) }, onClick = {
                                viewModel.sexo = opcion
                                dropdownSexoExpanded = false
                            })
                        }
                    }
                }

                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = if (viewModel.ocupacionSeleccionada.isEmpty()) "Seleccione una Ocupación" else "${viewModel.ocupacionSeleccionada} (RD$ ${viewModel.sueldoCalculado})",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Ocupación / Puesto") },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, Modifier.clickable { dropdownOcupacionExpanded = true }) },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Box(modifier = Modifier.matchParentSize().clickable { dropdownOcupacionExpanded = true })
                    DropdownMenu(expanded = dropdownOcupacionExpanded, onDismissRequest = { dropdownOcupacionExpanded = false }) {
                        if (listaOcupaciones.isEmpty()) {
                            DropdownMenuItem(text = { Text("Primero registre ocupaciones") }, onClick = { dropdownOcupacionExpanded = false })
                        } else {
                            listaOcupaciones.forEach { ocu ->
                                DropdownMenuItem(text = { Text("${ocu.Descripcion} - RD$ ${ocu.Sueldo}") }, onClick = {
                                    viewModel.ocupacionSeleccionada = ocu.Descripcion
                                    viewModel.sueldoCalculado = ocu.Sueldo
                                    dropdownOcupacionExpanded = false
                                })
                            }
                        }
                    }
                }

                if (viewModel.mensajeError.isNotEmpty()) {
                    Text(
                        text = viewModel.mensajeError,
                        color = if (viewModel.mensajeError.startsWith("Error")) Color.Red else Color(0xFF007F00),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { viewModel.guardar() }, modifier = Modifier.weight(1f)) {
                        Text(if (viewModel.empleadoAEditar == null) "Guardar" else "Actualizar")
                    }
                    if (viewModel.empleadoAEditar != null) {
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
                Text(text = "Empleados Registrados", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))

                ListaEmpleadosComponent(
                    listaEmpleados = listaEmpleados,
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
                text = if (viewModel.empleadoAEditar == null) "Registro de Empleados" else "Modificar Empleado",
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = viewModel.nombres,
                onValueChange = { viewModel.nombres = it },
                label = { Text("Nombres Completos") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = viewModel.fechaIngreso,
                onValueChange = { viewModel.fechaIngreso = it },
                label = { Text("Fecha de Ingreso (Ej: 28/05/2026)") },
                modifier = Modifier.fillMaxWidth()
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = if (viewModel.sexo.isEmpty()) "Seleccione el Sexo" else viewModel.sexo,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sexo") },
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, Modifier.clickable { dropdownSexoExpanded = true }) },
                    modifier = Modifier.fillMaxWidth()
                )
                Box(modifier = Modifier.matchParentSize().clickable { dropdownSexoExpanded = true })
                DropdownMenu(expanded = dropdownSexoExpanded, onDismissRequest = { dropdownSexoExpanded = false }) {
                    listOf("Masculino", "Femenino").forEach { opcion ->
                        DropdownMenuItem(text = { Text(opcion) }, onClick = {
                            viewModel.sexo = opcion
                            dropdownSexoExpanded = false
                        })
                    }
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = if (viewModel.ocupacionSeleccionada.isEmpty()) "Seleccione una Ocupación" else "${viewModel.ocupacionSeleccionada} (RD$ ${viewModel.sueldoCalculado})",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Ocupación / Puesto") },
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, Modifier.clickable { dropdownOcupacionExpanded = true }) },
                    modifier = Modifier.fillMaxWidth()
                )
                Box(modifier = Modifier.matchParentSize().clickable { dropdownOcupacionExpanded = true })
                DropdownMenu(expanded = dropdownOcupacionExpanded, onDismissRequest = { dropdownOcupacionExpanded = false }) {
                    if (listaOcupaciones.isEmpty()) {
                        DropdownMenuItem(text = { Text("Primero registre ocupaciones") }, onClick = { dropdownOcupacionExpanded = false })
                    } else {
                        listaOcupaciones.forEach { ocu ->
                            DropdownMenuItem(text = { Text("${ocu.Descripcion} - RD$ ${ocu.Sueldo}") }, onClick = {
                                viewModel.ocupacionSeleccionada = ocu.Descripcion
                                viewModel.sueldoCalculado = ocu.Sueldo
                                dropdownOcupacionExpanded = false
                            })
                        }
                    }
                }
            }

            if (viewModel.mensajeError.isNotEmpty()) {
                Text(
                    text = viewModel.mensajeError,
                    color = if (viewModel.mensajeError.startsWith("Error")) Color.Red else Color(0xFF007F00),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { viewModel.guardar() }, modifier = Modifier.weight(1f)) {
                    Text(if (viewModel.empleadoAEditar == null) "Guardar" else "Actualizar")
                }
                if (viewModel.empleadoAEditar != null) {
                    OutlinedButton(onClick = { viewModel.limpiarFormulario() }, modifier = Modifier.weight(1f)) {
                        Text("Cancelar")
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            Text(text = "Empleados Registrados", style = MaterialTheme.typography.titleMedium)

            Box(modifier = Modifier.weight(1f)) {
                ListaEmpleadosComponent(
                    listaEmpleados = listaEmpleados,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun ListaEmpleadosComponent(
    listaEmpleados: List<EmpleadoEntity>,
    viewModel: EmpleadoViewModel
) {
    LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (listaEmpleados.isEmpty()) {
            item { Text("No hay empleados en la base de datos.", color = Color.Gray) }
        }
        items(listaEmpleados) { emp ->
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = emp.Nombres, style = MaterialTheme.typography.bodyLarge)
                        Text(text = "${emp.Ocupacion} • RD$ ${String.format("%.2f", emp.Sueldo)}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                        Text(text = "Ingreso: ${emp.FechaIngreso} | Sexo: ${emp.Sexo}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                    }
                    Row {
                        IconButton(onClick = { viewModel.prepararEdicion(emp) }) {
                            Icon(Icons.Default.Edit, "Editar", tint = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { viewModel.eliminarEmpleado(emp) }) {
                            Icon(Icons.Default.Delete, "Eliminar", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}