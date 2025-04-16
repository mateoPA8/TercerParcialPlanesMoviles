package com.ucb.usecases

import com.ucb.data.libro.ILocalDataSources
import com.ucb.domain.Libro

class GuardarLibro(
    private val localDataSource: ILocalDataSources
) {
    suspend fun execute(libro: Libro): Boolean {
        return localDataSource.guardar(libro)
    }
}