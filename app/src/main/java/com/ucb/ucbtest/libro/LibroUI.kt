package com.ucb.ucbtest.libro

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LibroUI(viewModel: LibroViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState() // Obtenemos el estado del ViewModel
    var titulo by remember { mutableStateOf("") } // Guardamos el texto del campo de búsqueda

    Column(modifier = Modifier.padding(16.dp)) {
        // Campo de texto para ingresar el título del libro
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Buscar libro") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para buscar el libro
        Button(
            onClick = {
                if (titulo.isNotEmpty()) {
                    viewModel.loadLibros(titulo) // Llamamos al método para cargar los libros
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Buscar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mostramos el estado dependiendo de lo que suceda en la API
        when (state) {
            is LibroViewModel.LibroUIState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is LibroViewModel.LibroUIState.Error -> {
                Text("Error: ${(state as LibroViewModel.LibroUIState.Error).message}")
            }
            is LibroViewModel.LibroUIState.Loaded -> {
                val libros = (state as LibroViewModel.LibroUIState.Loaded).list
                if (libros.isEmpty()) {
                    Text("No se encontraron libros")
                } else {
                    LazyColumn {
                        items(libros) { libro ->
                            Column(modifier = Modifier.padding(8.dp)) {
                                // Mostramos el título del libro
                                Text("Título: ${libro.titulo}")
                                // Si existen autores, los mostramos
                                libro.autores?.let {
                                    Text("Autores: ${it.joinToString()}")
                                }
                                // Si el año de publicación está disponible, lo mostramos
                                libro.anioPublicacion?.let {
                                    Text("Año de publicación: $it")
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}