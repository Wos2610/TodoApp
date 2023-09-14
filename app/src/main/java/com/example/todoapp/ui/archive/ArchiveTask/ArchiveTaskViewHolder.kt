package com.example.todoapp.ui.archive.ArchiveTask

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemArchivedTaskBinding
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.task.enums.PriorityType
import com.example.todoapp.ui.task.enums.StatusType

class ArchiveTaskViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemArchivedTaskBinding.bind(view)

    fun bind(task : Task){
        binding.apply {
            itemTitle.text = task.title
            itemPriority.text = PriorityType.values()[task.priority].description
            itemStatus.text = StatusType.values()[task.status].description
        }
    }
}