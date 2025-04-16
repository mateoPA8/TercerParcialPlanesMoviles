package com.ucb.framework.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LibroResponseDto(
    @Json(name = "docs")
    val docs: List<LibroDto>?
)