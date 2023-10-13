package com.example.todoapp.ui.home.category

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTodayTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskWithCategoryTitle
import com.example.todoapp.viewModel.CategoryViewModel

class TaskAdapter(val view : (Task) -> Unit, private val categoryTitle : String) : RecyclerView.Adapter<TaskViewHolder>() {
    private lateinit var itemBinding : ItemTodayTaskBinding
    var tasks: List<Task> = listOf()
        get() {
            return field
        }
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_today_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        itemBinding = ItemTodayTaskBinding.bind(holder.itemView)
        val currentTask = tasks[position]

        holder.bind(currentTask, categoryTitle)

        holder.itemView.setOnClickListener{
            view(currentTask)
        }
    }
}