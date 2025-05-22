package com.ucb.ucbtest.planes


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.domain.Plan
import com.ucb.usecases.ObtenerPlanes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
@HiltViewModel
class PlanesViewModel @Inject constructor(
    private val obtenerPlanes: ObtenerPlanes
) : ViewModel() {

    private val _planes = MutableStateFlow<List<Plan>>(emptyList())
    val planes: StateFlow<List<Plan>> = _planes

    private val _indiceActual = MutableStateFlow(0)
    val indiceActual: StateFlow<Int> = _indiceActual

    init {
        cargarPlanes()
    }

    private fun cargarPlanes() {
        viewModelScope.launch {
            obtenerPlanes().collect { lista ->
                _planes.value = lista
                _indiceActual.value = 0 // reset al inicio
            }
        }
    }

    fun irIzquierda() {
        if (_planes.value.isNotEmpty()) {
            _indiceActual.value = (_indiceActual.value - 1 + _planes.value.size) % _planes.value.size
        }
    }

    fun irDerecha() {
        if (_planes.value.isNotEmpty()) {
            _indiceActual.value = (_indiceActual.value + 1) % _planes.value.size
        }
    }
}