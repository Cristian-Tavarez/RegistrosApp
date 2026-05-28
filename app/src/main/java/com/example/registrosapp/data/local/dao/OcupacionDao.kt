package com.example.registrosapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.registrosapp.data.local.entity.OcupacionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OcupacionDao {

    @Query("SELECT * FROM Ocupaciones")
    fun obtenerTodos(): Flow<List<OcupacionEntity>>
    @Query("SELECT * FROM Ocupaciones WHERE Descripcion = :descripcion LIMIT 1")
    suspend fun buscarPorDescripcion(descripcion: String): OcupacionEntity?
    @Update
    suspend fun actualizar(ocupacion: OcupacionEntity)
    @Delete
    suspend fun eliminar(ocupacion: OcupacionEntity)
    @Insert
    suspend fun insertar(ocupacion: OcupacionEntity)
}