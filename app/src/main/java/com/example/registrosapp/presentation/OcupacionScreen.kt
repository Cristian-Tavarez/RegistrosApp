package com.example.registrosapp.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun OcupacionScreen(viewModel: OcupacionViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Registro de Ocupaciones", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = viewModel.descripcion,
            onValueChange = { viewModel.descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.sueldo,
            onValueChange = { viewModel.sueldo = it },
            label = { Text("Sueldo") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.guardar() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }

        if (viewModel.mensajeError.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = viewModel.mensajeError,
                color = if (viewModel.mensajeError.contains("éxito")) Color(0xFF388E3C) else Color.Red
            )
        }
    }
}