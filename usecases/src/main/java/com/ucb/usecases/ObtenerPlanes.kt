package com.ucb.usecases


import com.ucb.domain.Plan


import com.ucb.data.PlanesRepository

import kotlinx.coroutines.flow.Flow


class ObtenerPlanes(
    private val repository: PlanesRepository
) {
    operator fun invoke(): Flow<List<Plan>> {
        return repository.obtenerPlanes()
    }
}