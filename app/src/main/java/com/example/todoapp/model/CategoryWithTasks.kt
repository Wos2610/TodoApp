package com.example.todoapp.model

import android.net.Uri
import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithTasks(
    @Embedded val category : Category,
    @Relation(
        entity = Task::class,
        parentColumn = "category_id",
        entityColumn = "categoryId"
    )
    val tasks : List<Task>
)