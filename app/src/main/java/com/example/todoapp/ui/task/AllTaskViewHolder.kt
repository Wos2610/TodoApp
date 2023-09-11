package com.example.todoapp.ui.task

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task

class AllTaskViewHolder(view : View) : RecyclerView.ViewHolder(view){
    private val itemBinding : ItemTaskBinding = ItemTaskBinding.bind(view)
    fun bind(task : Task){
        itemBinding.apply {
            itemTitle.text = task.title
        }
    }
}