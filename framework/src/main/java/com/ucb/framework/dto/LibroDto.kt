package com.ucb.framework.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LibroDto(
    @Json(name = "title")
    val titulo: String,
    @Json(name = "author_name")
    val autores: List<String>?,
    @Json(name = "first_publish_year")
    val anioPublicacion: Int?
)