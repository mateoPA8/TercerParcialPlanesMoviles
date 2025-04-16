package com.ucb.framework.mappers

import com.ucb.domain.Gitalias
import com.ucb.domain.Libro
import com.ucb.domain.Movie
import com.ucb.framework.dto.AvatarResponseDto
import com.ucb.framework.dto.LibroDto
import com.ucb.framework.dto.MovieDto
import com.ucb.framework.persistence.GitAccount
import com.ucb.framework.persistence.LibroEntity

fun AvatarResponseDto.toModel(): Gitalias {
    return Gitalias(
        login = login,
        avatarUrl = url
    )
}

fun Gitalias.toEntity(): GitAccount {
    return GitAccount(login)
}

fun GitAccount.toModel(): Gitalias {
    return Gitalias(
        alias,
        ""
    )
}

fun MovieDto.toModel(): Movie {
    return Movie(
        title = title,
        overview = overview,
        posterPath = posterPath
    )
}
fun LibroDto.toDomain(): Libro {
    return Libro(
        titulo = this.titulo,
        autores = this.autores ?: emptyList(),
        anioPublicacion = this.anioPublicacion ?: 0
    )
}

fun Libro.toEntity(): LibroEntity {
    return LibroEntity(
        titulo = this.titulo,
        autores = this.autores?.joinToString(",") ?: "",
        anio = this.anioPublicacion ?: 0
    )
}

fun LibroEntity.toModel(): Libro {
    return Libro(
        titulo = this.titulo,
        autores = this.autores.split(",").map { it.trim() },
        anioPublicacion = this.anio
    )
}