package com.example.roomdb_cahyo_06.room

import androidx.room.*

@Dao
interface HomeworkDao {
    @Insert
    suspend fun addHomework(homework: Homework)

    @Update
    suspend fun updateHomework(homework: Homework)

    @Delete
    suspend fun deleteHomework(homework: Homework)

    @Query("SELECT * FROM homework")
    suspend fun getHomeworks() : List<Homework>

    @Query("SELECT * FROM Homework WHERE id=:homework_id")
    suspend fun getHomework(homework_id: Int) : List<Homework>
}