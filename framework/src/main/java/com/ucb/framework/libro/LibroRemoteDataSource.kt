package com.ucb.framework.libro
import com.ucb.data.NetworkResult
import com.ucb.data.libro.ILibroRemoteDataSource
import com.ucb.domain.Libro
import com.ucb.framework.mappers.toDomain
import com.ucb.framework.service.RetrofitBuilder

class LibroRemoteDataSource(
    private val retrofitService: RetrofitBuilder
) : ILibroRemoteDataSource {

    override suspend fun buscarLibros(titulo: String): NetworkResult<List<Libro>> {
        val response = retrofitService.libroApiService.buscarLibros(titulo)
        return if (response.isSuccessful && response.body() != null) {
            val libros = response.body()!!.docs?.map { it.toDomain() } ?: emptyList()
            NetworkResult.Success(libros)
        } else {
            NetworkResult.Error(response.message())
        }
    }
}