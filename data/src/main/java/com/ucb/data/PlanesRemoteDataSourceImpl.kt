package com.ucb.data

import com.ucb.data.plan.IPlanesRemoteDataSource
import com.ucb.domain.Plan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
class PlanesRemoteDataSourceImpl : IPlanesRemoteDataSource {
    override fun obtenerPlanes(): Flow<List<Plan>> {
        return flow {
            emit(listOf(
                Plan("Plan A", 50, 6, 70),
                Plan("Plan B", 80, 12, 100),
                Plan("Plan C", 100, 20, 100), // sin descuento
            ))
        }
    }
}