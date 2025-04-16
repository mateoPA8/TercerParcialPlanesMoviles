package com.ucb.ucbtest.libro


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.data.NetworkResult
import com.ucb.domain.Libro
import com.ucb.usecases.BuscarLibros
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibroViewModel @Inject constructor(
    private val buscarLibros: BuscarLibros
) : ViewModel() {

    sealed class LibroUIState {
        object Loading : LibroUIState()
        data class Loaded(val list: List<Libro>) : LibroUIState()
        data class Error(val message: String) : LibroUIState()
    }

    private val _state = MutableStateFlow<LibroUIState>(LibroUIState.Loading)
    val state: StateFlow<LibroUIState> = _state

    fun loadLibros(titulo: String) {
        _state.value = LibroUIState.Loading
        viewModelScope.launch {
            val response = buscarLibros.execute(titulo)
            when (val result = response) {
                is NetworkResult.Error -> {
                    _state.value = LibroUIState.Error(result.error)
                }
                is NetworkResult.Success -> {
                    _state.value = LibroUIState.Loaded(result.data)
                }
            }
        }
    }
}