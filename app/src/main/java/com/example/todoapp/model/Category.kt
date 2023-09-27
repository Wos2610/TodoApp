package com.example.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CATEGORY_TABLE")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id") val id : Int,
    @ColumnInfo(name = "categoryTitle") val title : String,
    @ColumnInfo val imageName : String,
    @ColumnInfo val completedPercentage : Float)