package com.example.registrosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.example.registrosapp.data.local.database.OcupacionesDb
import com.example.registrosapp.domain.use_case.ocupacion.ActualizarOcupacionUseCase
import com.example.registrosapp.domain.use_case.ocupacion.EliminarOcupacionUseCase
import com.example.registrosapp.domain.use_case.ocupacion.GetOcupacionesUseCase
import com.example.registrosapp.domain.use_case.ocupacion.GuardarOcupacionUseCase
import com.example.registrosapp.domain.use_case.empleado.ActualizarEmpleadoUseCase
import com.example.registrosapp.domain.use_case.empleado.EliminarEmpleadoUseCase
import com.example.registrosapp.domain.use_case.empleado.GetEmpleadosUseCase
import com.example.registrosapp.domain.use_case.empleado.GuardarEmpleadoUseCase
import com.example.registrosapp.presentation.EmpleadoScreen
import com.example.registrosapp.presentation.EmpleadoViewModel
import com.example.registrosapp.presentation.OcupacionScreen
import com.example.registrosapp.presentation.OcupacionViewModel
import com.example.registrosapp.presentation.HoraExtraScreen
import com.example.registrosapp.presentation.HoraExtraViewModel
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

        val getOcupacionesUseCase = GetOcupacionesUseCase(db.ocupacionDao())
        val guardarOcupacionUseCase = GuardarOcupacionUseCase(db.ocupacionDao())
        val actualizarOcupacionUseCase = ActualizarOcupacionUseCase(db.ocupacionDao())
        val eliminarOcupacionUseCase = EliminarOcupacionUseCase(db.ocupacionDao())

        val ocupacionViewModel = OcupacionViewModel(
            getOcupacionesUseCase = getOcupacionesUseCase,
            guardarOcupacionUseCase = guardarOcupacionUseCase,
            actualizarOcupacionUseCase = actualizarOcupacionUseCase,
            eliminarOcupacionUseCase = eliminarOcupacionUseCase
        )

        val getEmpleadosUseCase = GetEmpleadosUseCase(db.empleadoDao())
        val guardarEmpleadoUseCase = GuardarEmpleadoUseCase(db.empleadoDao())
        val actualizarEmpleadoUseCase = ActualizarEmpleadoUseCase(db.empleadoDao())
        val eliminarEmpleadoUseCase = EliminarEmpleadoUseCase(db.empleadoDao())

        val empleadoViewModel = EmpleadoViewModel(
            getEmpleadosUseCase = getEmpleadosUseCase,
            guardarEmpleadoUseCase = guardarEmpleadoUseCase,
            actualizarEmpleadoUseCase = actualizarEmpleadoUseCase,
            eliminarEmpleadoUseCase = eliminarEmpleadoUseCase
        )

        val horaExtraViewModel = HoraExtraViewModel(db.horaExtraDao())

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
                                        scope.launch { drawerState.close() }
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

                                Spacer(modifier = Modifier.height(8.dp))

                                NavigationDrawerItem(
                                    icon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                                    label = { Text("Horas Extras") },
                                    selected = pantallaActual == "horas_extras",
                                    onClick = {
                                        pantallaActual = "horas_extras"
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
                                        Text(
                                            when(pantallaActual) {
                                                "ocupaciones" -> "Ocupaciones"
                                                "empleados" -> "Empleados"
                                                else -> "Horas Extras"
                                            }
                                        )
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            scope.launch { drawerState.open() }
                                        }) {
                                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
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
                                when(pantallaActual) {
                                    "ocupaciones" -> OcupacionScreen(ocupacionViewModel)

                                    "empleados" -> {
                                        val listaOcupacionesReal by ocupacionViewModel.ocupacionesList.collectAsState(initial = emptyList())
                                        EmpleadoScreen(
                                            viewModel = empleadoViewModel,
                                            listaOcupaciones = listaOcupacionesReal
                                        )
                                    }

                                    "horas_extras" -> {
                                        val listaReal by empleadoViewModel.empleadosList.collectAsState(initial = emptyList())
                                        HoraExtraScreen(listaEmpleados = listaReal, horaExtraViewModel = horaExtraViewModel)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}