package com.example.registrosapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.registrosapp.data.local.entity.HoraExtraEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HoraExtraDao {
    @Insert
    suspend fun insertar(horaExtra: HoraExtraEntity)
    @Delete
    suspend fun eliminar(horaExtra: HoraExtraEntity)
    @Query("SELECT * FROM HorasExtras")
    fun obtenerTodos(): Flow<List<HoraExtraEntity>>
}