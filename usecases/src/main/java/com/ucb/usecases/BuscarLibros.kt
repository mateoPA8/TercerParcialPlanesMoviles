package com.ucb.usecases

import com.ucb.data.LibroRepository
import com.ucb.data.NetworkResult
import com.ucb.domain.Libro

class BuscarLibros(
    private val libroRepository: LibroRepository
) {
    suspend fun execute(titulo: String): NetworkResult<List<Libro>> {
        return libroRepository.buscarLibros(titulo)
    }
}