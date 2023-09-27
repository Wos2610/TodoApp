package com.example.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion

@Entity(tableName = "TASK_TABLE",
    foreignKeys = [ForeignKey(entity = Category::class,
                            parentColumns = ["category_id"],
                            childColumns = ["categoryId"],
                            onDelete = ForeignKey.CASCADE)])
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId") val id : Int,
    @ColumnInfo(name = "taskTitle") val title : String,
    @ColumnInfo val dueDate : String,
    @ColumnInfo val timeStart : String,
    @ColumnInfo val timeEnd : String,
    @ColumnInfo val categoryId : Int,
    @ColumnInfo var status : Int,
    @ColumnInfo var priority : Int,
    @ColumnInfo val description : String,
    @ColumnInfo var isArchive : Boolean)