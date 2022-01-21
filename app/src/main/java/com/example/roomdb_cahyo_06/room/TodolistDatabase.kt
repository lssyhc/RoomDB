package com.example.roomdb_cahyo_06.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Todolist::class],
    version = 1
)

abstract class TodolistDatabase : RoomDatabase(){

    abstract fun todolistDao(): TodolistDao

    companion object{

        @Volatile private var instance : TodolistDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            TodolistDatabase::class.java,
            "todolist123.db"
        ).build()

    }

}