package com.example.todoapp.model

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithTasks(
    @Embedded val category : Category,
    @Relation(
        entity = Task::class,
        parentColumn = "category_id",
        entityColumn = "task_id"
    )
    val tasks : List<Task>
)