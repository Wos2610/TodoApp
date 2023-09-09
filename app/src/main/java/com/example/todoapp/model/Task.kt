package com.example.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TASK_TABLE")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val id : Int,
    @ColumnInfo val title : String,
    @ColumnInfo val dueDate : String,
    @ColumnInfo val timeStart : String,
    @ColumnInfo val timeEnd : String,
    @ColumnInfo val categoryId : Int,
    @ColumnInfo val status : Int,
    @ColumnInfo val priority : Int,
    @ColumnInfo val description : String)
