package com.example.registrosapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.registrosapp.data.local.dao.OcupacionDao
import com.example.registrosapp.data.local.dao.EmpleadoDao
import com.example.registrosapp.data.local.dao.HoraExtraDao
import com.example.registrosapp.data.local.entity.OcupacionEntity
import com.example.registrosapp.data.local.entity.EmpleadoEntity
import com.example.registrosapp.data.local.entity.HoraExtraEntity

@Database(entities = [OcupacionEntity::class, EmpleadoEntity::class, HoraExtraEntity::class], version = 2, exportSchema = false)
abstract class OcupacionesDb : RoomDatabase() {
    abstract fun ocupacionDao(): OcupacionDao
    abstract fun empleadoDao(): EmpleadoDao
    abstract fun horaExtraDao(): HoraExtraDao
}