package com.example.registrosapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.registrosapp.data.local.entity.EmpleadoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmpleadoDao {
    @Query("SELECT * FROM Empleados")
    fun obtenerTodos(): Flow<List<EmpleadoEntity>>
    @Query("SELECT * FROM Empleados WHERE Nombres = :nombre LIMIT 1")
    suspend fun buscarPorNombre(nombre: String): EmpleadoEntity?

    @Update
    suspend fun actualizar(empleado: EmpleadoEntity)
    @Delete
    suspend fun eliminar(empleado: EmpleadoEntity)
    @Insert
    suspend fun insertar(empleado: EmpleadoEntity)
}