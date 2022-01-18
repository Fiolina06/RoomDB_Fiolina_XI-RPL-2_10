package com.example.crud_fiolina_10.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.locks.Lock

@Database(
    entities = [Movie::class],
    version = 1
)
abstract class MovieDB : RoomDatabase(){

    abstract fun MovieDao() : MovieDao

    companion object {
        @Volatile private var instance : MovieDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MovieDB::class.java,
            "movie12345.db"
        ).build()

    }
}
