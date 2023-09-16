package com.example.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion

@Entity(tableName = "TASK_TABLE")
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val id : Int,
    @ColumnInfo val title : String,
    @ColumnInfo val dueDate : String,
    @ColumnInfo val timeStart : String,
    @ColumnInfo val timeEnd : String,
    @ColumnInfo val categoryId : Int,
    @ColumnInfo var status : Int,
    @ColumnInfo var priority : Int,
    @ColumnInfo val description : String,
    @ColumnInfo var isArchive : Boolean)