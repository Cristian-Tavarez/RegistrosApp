package com.example.registrosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.registrosapp.data.OcupacionesDb
import com.example.registrosapp.presentation.OcupacionScreen
import com.example.registrosapp.presentation.OcupacionViewModel
import com.example.registrosapp.ui.theme.RegistrosAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val db = Room.databaseBuilder(
            applicationContext,
            OcupacionesDb::class.java,
            "ocupaciones.db"
        ).fallbackToDestructiveMigration()
            .build()


        val dao = db.ocupacionDao()
        val viewModel = OcupacionViewModel(dao)

        setContent {
            RegistrosAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    OcupacionScreen(viewModel)
                }
            }
        }
    }
}