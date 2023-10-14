package com.example.todoapp.ui.calendar

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemCalendarTaskBinding
import com.example.todoapp.databinding.ItemTodayTaskBinding
import com.example.todoapp.model.TaskWithCategoryTitle
import com.example.todoapp.ui.task.enums.StatusType

class TaskCalendarViewHolder(view : View) : RecyclerView.ViewHolder(view){
    private val binding = ItemCalendarTaskBinding.bind(view)
    fun bind(task : TaskWithCategoryTitle){
        binding.apply {
//            taskTitle.text = task.title.split(' ').joinToString(" ") { it.replaceFirstChar {char ->
//                if (char.isLowerCase()) char.titlecase(
//                    Locale.getDefault()
//                ) else char.toString()
//            } }
            taskTitle.text = task.taskTitle
            taskTime.text = task.timeStart + " - " + task.timeEnd
        }
    }
}