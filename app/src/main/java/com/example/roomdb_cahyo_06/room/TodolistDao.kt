package com.example.roomdb_cahyo_06.room

import androidx.room.*

@Dao
interface TodolistDao {
    @Insert
    suspend fun addTodolist(todolist: Todolist)

    @Update
    suspend fun updateTodolist(todolist: Todolist)

    @Delete
    suspend fun deleteTodolist(todolist: Todolist)

    @Query("SELECT * FROM todolist")
    suspend fun getTodolists() : List<Todolist>

    @Query("SELECT * FROM Todolist WHERE id=:todolist_id")
    suspend fun getTodolist(todolist_id: Int) : List<Todolist>
}