package com.example.registrosapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OcupacionDao {
    @Insert
    suspend fun insertar(ocupacion: OcupacionEntity)

    @Query("SELECT * FROM Ocupaciones WHERE Descripcion = :descripcion LIMIT 1")
    suspend fun buscarPorDescripcion(descripcion: String): OcupacionEntity?
}