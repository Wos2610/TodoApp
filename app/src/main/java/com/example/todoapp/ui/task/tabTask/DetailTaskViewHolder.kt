package com.example.todoapp.ui.task.tabTask

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.task.enums.StatusType
import com.example.todoapp.ui.task.enums.PriorityType


class AllTaskViewHolder(view : View) : RecyclerView.ViewHolder(view){
    private val itemBinding : ItemTaskBinding = ItemTaskBinding.bind(view)
    fun bind(task : Task){
        itemBinding.apply {
            itemTitle.text = task.title
            itemPriority.text = PriorityType.values()[task.priority].description
            itemStatus.text = StatusType.values()[task.status].description
        }
    }
}