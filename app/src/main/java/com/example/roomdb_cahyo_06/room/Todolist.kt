package com.example.roomdb_cahyo_06.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todolist(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val desc: String
)