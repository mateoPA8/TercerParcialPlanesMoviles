package com.ucb.framework.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface ILibroDAO {

    @Query("SELECT * FROM libro_favorito")
    fun obtenerLibros(): List<LibroEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarLibro(libro: LibroEntity)

    @Query("DELETE FROM libro_favorito")
    suspend fun eliminarTodos()

    @Query("SELECT * FROM libro_favorito WHERE titulo = :titulo LIMIT 1")
    suspend fun buscarPorTitulo(titulo: String): LibroEntity
}