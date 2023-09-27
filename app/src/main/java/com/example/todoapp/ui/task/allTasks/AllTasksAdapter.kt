package com.example.todoapp.ui.task.allTasks

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.TaskWithCategoryTitle
import com.example.todoapp.ui.task.tabTask.TabTaskViewHolder

class AllTasksAdapter(private val update: (TaskWithCategoryTitle) -> Unit, private val archive: (TaskWithCategoryTitle) -> Unit) : RecyclerView.Adapter<AllTasksViewHolder>() {
    private lateinit var itemBinding: ItemTaskBinding
    var tasks: List<TaskWithCategoryTitle> = listOf()
        get() {
            return field
        }
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTasksViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return AllTasksViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllTasksViewHolder, position: Int) {
        itemBinding = ItemTaskBinding.bind(holder.itemView)
        val currentTask = tasks[position]

        holder.bind(currentTask)
        itemBinding.dragItem.setOnClickListener{
            update(currentTask)
        }
        itemBinding.archiveButton.setOnClickListener{
            archive(currentTask)
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}