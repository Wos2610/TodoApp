package com.example.todoapp.ui.task

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task

enum class PriorityType(val value : Int, val description : String){
    NULL(0, "Null"),
    LOW(1, "Low"),
    MID(2, "Mid"),
    HIGH(3, "High")
}

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