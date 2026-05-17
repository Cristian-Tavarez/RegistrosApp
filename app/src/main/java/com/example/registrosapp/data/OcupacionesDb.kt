package com.example.registrosapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [OcupacionEntity::class, EmpleadoEntity::class],
    version = 2
)
abstract class OcupacionesDb : RoomDatabase() {
    abstract fun ocupacionDao(): OcupacionDao
    abstract fun empleadoDao(): EmpleadoDao
}