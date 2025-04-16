package com.ucb.data.libro

import com.ucb.domain.Libro

interface ILocalDataSources {
    suspend fun guardar(libro: Libro): Boolean
    //suspend fun findByTitulo(titulo: String): List<Libro>
    //suspend fun getAllLibros(): List<Libro>
}