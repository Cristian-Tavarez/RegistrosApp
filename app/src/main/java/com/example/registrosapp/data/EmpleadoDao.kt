package com.example.registrosapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EmpleadoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertar(empleado: EmpleadoEntity)
    @Query("SELECT * FROM Empleados WHERE Nombres = :nombres LIMIT 1")
    suspend fun buscarPorNombre(nombres: String): EmpleadoEntity?
}