package com.ucb.data

import com.ucb.data.plan.IPlanesRemoteDataSource
import com.ucb.domain.Plan
import kotlinx.coroutines.flow.Flow


class PlanesRepository(
    private val remoteDataSource: IPlanesRemoteDataSource
) {
    fun obtenerPlanes(): Flow<List<Plan>> {
        return remoteDataSource.obtenerPlanes()
    }
}