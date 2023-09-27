package com.example.todoapp.model

data class TaskWithCategoryTitle(
    val taskId : Int,
    val taskTitle : String,
    val dueDate : String,
    val timeStart : String,
    val timeEnd : String,
    val categoryId : Int,
    val categoryTitle : String,
    var status : Int,
    var priority : Int,
    val description : String,
    var isArchive : Boolean
)