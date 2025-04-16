package com.ucb.ucbtest.libro

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LibroUI(viewModel: LibroViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    var titulo by remember { mutableStateOf("") }


    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Buscar libro") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (titulo.isNotEmpty()) {
                    viewModel.loadLibros(titulo)
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Buscar")
        }

        Spacer(modifier = Modifier.height(16.dp))

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

                                Text("Título: ${libro.titulo}")

                                libro.autores?.let {
                                    Text("Autores: ${it.joinToString()}")
                                }

                                libro.anioPublicacion?.let {
                                    Text("Año de publicación: $it")
                                }


                                Button(
                                    onClick = {
                                        viewModel.guardarLibro(libro)
                                        Toast.makeText(context, "Libro guardado", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.padding(top = 8.dp)
                                ) {
                                    Text("Guardar libro")
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }

            LibroViewModel.LibroUIState.Guardado -> TODO()
        }
    }
}