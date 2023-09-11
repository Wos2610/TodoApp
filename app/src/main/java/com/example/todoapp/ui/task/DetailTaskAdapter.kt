package com.example.todoapp.ui.task

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task

class DetailTaskAdapter(val update: (Task) -> Unit, val delete: (Task) -> Unit) : RecyclerView.Adapter<AllTaskViewHolder>() {
    private lateinit var itemBinding: ItemTaskBinding
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
        itemBinding = ItemTaskBinding.bind(holder.itemView)
        val currentTask = tasks[position]
        holder.bind(currentTask)
        itemBinding.dragItem.setOnClickListener{
            update(currentTask)
        }
        itemBinding.deleteButton.setOnClickListener{
            delete(currentTask)
        }
    }
}