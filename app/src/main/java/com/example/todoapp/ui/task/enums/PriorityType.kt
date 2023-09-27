package com.example.todoapp.ui.task.enums

import com.example.todoapp.R

enum class PriorityType(val value : Int, val description : String, val idColor : Int){
    NULL(0, "Null", R.color.white),
    LOW(1, "Low", R.color.green),
    MID(2, "Mid", R.color.yellow),
    HIGH(3, "High", R.color.orange)
}