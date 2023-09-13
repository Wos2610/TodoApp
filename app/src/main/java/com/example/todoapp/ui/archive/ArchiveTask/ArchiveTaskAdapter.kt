package com.example.todoapp.ui.archive.ArchiveTask

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task

class ArchiveTaskAdapter() : RecyclerView.Adapter<ArchiveTaskViewHolder>() {
    private lateinit var itemBinding : ItemTaskBinding
    var tasks: List<Task> = listOf()
        get() {
            return field
        }
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveTaskViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_today_task, parent, false)
        return ArchiveTaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: ArchiveTaskViewHolder, position: Int) {
        itemBinding = ItemTaskBinding.bind(holder.itemView)
        val currentTask = tasks[position]
        holder.bind(currentTask)
    }
}