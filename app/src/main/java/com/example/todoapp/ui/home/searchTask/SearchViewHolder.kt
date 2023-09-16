package com.example.todoapp.ui.home.searchTask

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTodayTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.task.enums.StatusType

class SearchViewHolder(view : View) : RecyclerView.ViewHolder(view){
    private val binding = ItemTodayTaskBinding.bind(view)
    fun bind(task : Task){
        binding.apply {
            taskTitle.text = task.title
            taskTime.text = task.timeStart + " - " + task.timeEnd
            taskStatus.text = StatusType.values()[task.status].description
        }
    }
}