package com.ucb.framework.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LibroEntity::class], version = 1, exportSchema = false)
abstract class LibroRoomDatabase : RoomDatabase() {
    abstract fun libroDao(): ILibroDAO

    companion object {
        @Volatile
        private var INSTANCE: LibroRoomDatabase? = null

        fun getDatabase(context: Context): LibroRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    LibroRoomDatabase::class.java,
                    "libro_favorito_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
