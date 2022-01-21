package com.example.roomdb_cahyo_06.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Homework::class],
    version = 1
)

abstract class HomeworkDatabase : RoomDatabase(){

    abstract fun homeworkDao(): HomeworkDao

    companion object{

        @Volatile private var instance : HomeworkDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            HomeworkDatabase::class.java,
            "homework123.db"
        ).build()

    }

}