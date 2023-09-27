package com.example.todoapp.ui.task.tabTask

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskWithCategoryTitle
import com.example.todoapp.ui.task.enums.StatusType
import com.example.todoapp.ui.task.enums.PriorityType
import java.util.Locale


class TabTaskViewHolder(view : View) : RecyclerView.ViewHolder(view){
    private val itemBinding : ItemTaskBinding = ItemTaskBinding.bind(view)
    fun bind(task : TaskWithCategoryTitle){
        itemBinding.apply {
//            itemTitle.text = task.title.split(' ').joinToString(" ") { it.replaceFirstChar {char ->
//                if (char.isLowerCase()) char.titlecase(
//                    Locale.getDefault()
//                ) else char.toString()
//            } }
            Log.d("TabTaskViewHolder", task.toString())
            itemTitle.text = task.taskTitle
            itemCategoryTitle.text = task.categoryTitle
            itemPriority.text = PriorityType.values()[task.priority].description
            itemPriority.setTextColor(ContextCompat.getColor(itemView.context, PriorityType.values()[task.priority].idColor))
            itemDueDate.text = task.dueDate
        }
    }
}