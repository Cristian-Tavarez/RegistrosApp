package com.example.registrosapp.di

import android.content.Context
import androidx.room.Room
import com.example.registrosapp.data.local.dao.EmpleadoDao
import com.example.registrosapp.data.local.dao.OcupacionDao
import com.example.registrosapp.data.local.dao.HoraExtraDao
import com.example.registrosapp.data.local.database.OcupacionesDb
import com.example.registrosapp.domain.use_case.ocupacion.ActualizarOcupacionUseCase
import com.example.registrosapp.domain.use_case.ocupacion.EliminarOcupacionUseCase
import com.example.registrosapp.domain.use_case.ocupacion.GetOcupacionesUseCase
import com.example.registrosapp.domain.use_case.ocupacion.GuardarOcupacionUseCase
import com.example.registrosapp.domain.use_case.empleado.ActualizarEmpleadoUseCase
import com.example.registrosapp.domain.use_case.empleado.EliminarEmpleadoUseCase
import com.example.registrosapp.domain.use_case.empleado.GetEmpleadosUseCase
import com.example.registrosapp.domain.use_case.empleado.GuardarEmpleadoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): OcupacionesDb {
        return Room.databaseBuilder(
            context,
            OcupacionesDb::class.java,
            "ocupaciones.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideOcupacionDao(db: OcupacionesDb): OcupacionDao = db.ocupacionDao()

    @Provides
    @Singleton
    fun provideEmpleadoDao(db: OcupacionesDb): EmpleadoDao = db.empleadoDao()

    @Provides
    @Singleton
    fun provideHoraExtraDao(db: OcupacionesDb): HoraExtraDao = db.horaExtraDao()

    @Provides
    @Singleton
    fun provideGetOcupacionesUseCase(dao: OcupacionDao) = GetOcupacionesUseCase(dao)

    @Provides
    @Singleton
    fun provideGuardarOcupacionUseCase(dao: OcupacionDao) = GuardarOcupacionUseCase(dao)

    @Provides
    @Singleton
    fun provideActualizarOcupacionUseCase(dao: OcupacionDao) = ActualizarOcupacionUseCase(dao)

    @Provides
    @Singleton
    fun provideEliminarOcupacionUseCase(dao: OcupacionDao) = EliminarOcupacionUseCase(dao)

    @Provides
    @Singleton
    fun provideGetEmpleadosUseCase(dao: EmpleadoDao) = GetEmpleadosUseCase(dao)

    @Provides
    @Singleton
    fun provideGuardarEmpleadoUseCase(dao: EmpleadoDao) = GuardarEmpleadoUseCase(dao)

    @Provides
    @Singleton
    fun provideActualizarEmpleadoUseCase(dao: EmpleadoDao) = ActualizarEmpleadoUseCase(dao)

    @Provides
    @Singleton
    fun provideEliminarEmpleadoUseCase(dao: EmpleadoDao) = EliminarEmpleadoUseCase(dao)
}