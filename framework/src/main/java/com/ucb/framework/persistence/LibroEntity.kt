package com.ucb.framework.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "libro_favorito")
data class LibroEntity(
    @ColumnInfo(name = "titulo")
    var titulo: String,

    @ColumnInfo(name = "autores")
    var autores: String, // Guardamos como String separados por comas

    @ColumnInfo(name = "anio")
    var anio: Int
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}