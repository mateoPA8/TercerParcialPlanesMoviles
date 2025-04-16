package com.ucb.framework.libro

import android.content.Context
import com.ucb.data.libro.ILocalDataSources
import com.ucb.domain.Libro
import com.ucb.framework.mappers.toEntity
import com.ucb.framework.mappers.toModel
import com.ucb.framework.persistence.LibroRoomDatabase

class LibroLocalDataSource(context: Context) : ILocalDataSources {
    private val libroDAO = LibroRoomDatabase.getDatabase(context).libroDao()
    override suspend fun guardar(libro: Libro): Boolean {
        libroDAO.insertarLibro(libro.toEntity())
        return true
    }

    suspend fun obtenerTodos(): List<Libro> {
        return libroDAO.obtenerLibros().map { it.toModel() }
    }

    suspend fun buscarPorTitulo(titulo: String): Libro {
        return libroDAO.buscarPorTitulo(titulo).toModel()
    }

    suspend fun borrarTodo() {
        libroDAO.eliminarTodos()
    }
}