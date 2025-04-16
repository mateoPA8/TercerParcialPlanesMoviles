package com.ucb.framework.service

import com.ucb.framework.dto.LibroResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LibroApiService {

    @GET("search.json")
    suspend fun buscarLibros(
        @Query("q") titulo: String
    ): Response<LibroResponseDto>
}