package com.example.todoapp.ui.task

enum class StatusType(val value : Int, val description: String){
    NULL(0, "Null"),
    TODO(1, "Todo"),
    ON_PROGRESS(2, "On Progress"),
    DONE(3, "Done")
}