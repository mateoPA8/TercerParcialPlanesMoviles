package com.ucb.data


import com.ucb.data.libro.ILibroRemoteDataSource
import com.ucb.domain.Libro

class LibroRepository(
    val remoteDataSource: ILibroRemoteDataSource
) {

    suspend fun buscarLibros(titulo: String): NetworkResult<List<Libro>> {
        return this.remoteDataSource.buscarLibros(titulo)
    }
}