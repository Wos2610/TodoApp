package com.example.todoapp.ui.task

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task

class AllTaskAdapter(val update: (Task) -> Unit, val delete: (Task) -> Unit) : RecyclerView.Adapter<AllTaskViewHolder>() {
    var tasks: List<Task> = listOf()
        get() {
            return field
        }
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTaskViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return AllTaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: AllTaskViewHolder, position: Int) {
        val currentTask = tasks[position]
        holder.bind(currentTask)
        holder.itemView.setOnClickListener{
            update(currentTask)
        }
    }


}