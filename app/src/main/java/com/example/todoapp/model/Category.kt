package com.example.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CATEGORY_TABLE")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo val id : Int,
    @ColumnInfo val title : String,
    @ColumnInfo val imageName : String,
    @ColumnInfo val completedPercentage : Float)