package com.ucb.data.libro

import com.ucb.data.NetworkResult
import com.ucb.domain.Libro

interface ILibroRemoteDataSource {
    suspend fun buscarLibros(titulo: String): NetworkResult<List<Libro>>
}