package com.example.todoapp.ui.home.todayTask

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTodayTaskBinding
import com.example.todoapp.model.TaskWithCategoryTitle
import com.example.todoapp.ui.task.enums.StatusType

class TodayTaskViewHolder(view : View) : RecyclerView.ViewHolder(view){
    private val binding = ItemTodayTaskBinding.bind(view)
    fun bind(task : TaskWithCategoryTitle){
        binding.apply {
//            taskTitle.text = task.title.split(' ').joinToString(" ") { it.replaceFirstChar {char ->
//                if (char.isLowerCase()) char.titlecase(
//                    Locale.getDefault()
//                ) else char.toString()
//            } }
            taskTitle.text = task.taskTitle
            categoryTitle.text = task.categoryTitle
            taskTime.text = task.timeStart + " - " + task.timeEnd
            taskStatus.text = StatusType.values()[task.status].description
        }
    }
}
