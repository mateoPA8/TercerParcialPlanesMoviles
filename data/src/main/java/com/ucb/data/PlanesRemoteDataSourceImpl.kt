package com.ucb.data

import com.ucb.data.plan.IPlanesRemoteDataSource
import com.ucb.domain.Plan
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
class PlanesRemoteDataSourceImpl : IPlanesRemoteDataSource {
    override fun obtenerPlanes(): Flow<List<Plan>> {
        return flow {
            emit(listOf(
                Plan("Plan FLEX 5", 199, 5, 270),
                Plan("Plan FLEX 8", 299, 8, 370),
                Plan("Plan FLEX 10", 399, 10, 470),
            ))
        }
    }
}