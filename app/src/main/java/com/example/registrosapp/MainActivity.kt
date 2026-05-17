package com.example.registrosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.registrosapp.data.OcupacionesDb
import com.example.registrosapp.presentation.EmpleadoScreen
import com.example.registrosapp.presentation.EmpleadoViewModel
import com.example.registrosapp.presentation.OcupacionScreen
import com.example.registrosapp.presentation.OcupacionViewModel
import com.example.registrosapp.ui.theme.RegistrosAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            OcupacionesDb::class.java,
            "ocupaciones.db"
        ).fallbackToDestructiveMigration().build()

        val ocupacionViewModel = OcupacionViewModel(db.ocupacionDao())
        val empleadoViewModel = EmpleadoViewModel(db.empleadoDao())

        setContent {
            RegistrosAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    var pantallaActual by remember { mutableStateOf("ocupaciones") }

                    ModalNavigationDrawer(
                        drawerState = drawerState,
                        drawerContent = {
                            ModalDrawerSheet {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Menú de Registros",
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(16.dp)
                                )

                                HorizontalDivider()

                                Spacer(modifier = Modifier.height(8.dp))

                                NavigationDrawerItem(
                                    icon = { Icon(Icons.Default.Star, contentDescription = null) },
                                    label = { Text("Registrar Ocupaciones") },
                                    selected = pantallaActual == "ocupaciones",
                                    onClick = {
                                        pantallaActual = "ocupaciones"
                                        scope.launch { drawerState.close() } // Cierra el menú al pulsar
                                    },
                                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                NavigationDrawerItem(
                                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                                    label = { Text("Registrar Empleados") },
                                    selected = pantallaActual == "empleados",
                                    onClick = {
                                        pantallaActual = "empleados"
                                        scope.launch { drawerState.close() }
                                    },
                                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                                )
                            }
                        }
                    ) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = {
                                        Text(if (pantallaActual == "ocupaciones") "Ocupaciones" else "Empleados")
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            scope.launch { drawerState.open() }
                                        }) {
                                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú lateral")
                                        }
                                    },
                                    colors = TopAppBarDefaults.topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    )
                                )
                            }
                        ) { paddingValues ->
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                            ) {
                                if (pantallaActual == "ocupaciones") {
                                    OcupacionScreen(ocupacionViewModel)
                                } else {
                                    EmpleadoScreen(empleadoViewModel)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}