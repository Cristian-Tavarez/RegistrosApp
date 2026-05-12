package com.example.registrosapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [OcupacionEntity::class], version = 1)
abstract class OcupacionesDb : RoomDatabase() {
    abstract fun ocupacionDao(): OcupacionDao
}