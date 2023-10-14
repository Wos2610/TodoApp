package com.example.todoapp.ui.calendar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemCalendarTaskBinding
import com.example.todoapp.model.TaskWithCategoryTitle

class TaskCalendarAdapter(val view : (TaskWithCategoryTitle) -> Unit) : RecyclerView.Adapter<TaskCalendarViewHolder>() {
    private lateinit var itemBinding : ItemCalendarTaskBinding
    var tasks: List<TaskWithCategoryTitle> = listOf()
        get() {
            return field
        }
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskCalendarViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_task, parent, false)
        return TaskCalendarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskCalendarViewHolder, position: Int) {
        itemBinding = ItemCalendarTaskBinding.bind(holder.itemView)
        val currentTask = tasks[position]

        holder.bind(currentTask)

        holder.itemView.setOnClickListener{
            view(currentTask)
        }
    }
}