package com.ucb.data.plan

import com.ucb.domain.Plan
import kotlinx.coroutines.flow.Flow

interface IPlanesRemoteDataSource {
    fun obtenerPlanes(): Flow<List<Plan>>
}