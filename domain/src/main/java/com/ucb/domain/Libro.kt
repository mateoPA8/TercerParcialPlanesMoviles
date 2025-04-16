package com.ucb.domain

data class Libro(
    val titulo: String,
    val autores: List<String>?,
    val anioPublicacion: Int?
)