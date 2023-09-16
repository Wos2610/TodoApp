package com.example.todoapp.ui.archive.ArchiveTask

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemArchivedTaskBinding
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task

class ArchiveTaskAdapter(private val delete : (Task) -> Unit, private val restore : (Task) -> Unit) : RecyclerView.Adapter<ArchiveTaskViewHolder>() {
    private lateinit var itemBinding : ItemArchivedTaskBinding
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
            .inflate(R.layout.item_archived_task, parent, false)
        return ArchiveTaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: ArchiveTaskViewHolder, position: Int) {
        itemBinding = ItemArchivedTaskBinding.bind(holder.itemView)
        val currentTask = tasks[position]
        holder.bind(currentTask)

        itemBinding.deleteButton.setOnClickListener{
            delete(currentTask)
        }

        itemBinding.restoreButton.setOnClickListener{
            restore(currentTask)
        }
    }
}