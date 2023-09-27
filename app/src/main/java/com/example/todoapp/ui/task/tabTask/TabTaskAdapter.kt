package com.example.todoapp.ui.task.tabTask

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskWithCategoryTitle
import com.example.todoapp.viewModel.CategoryViewModel

class TabTaskAdapter(private val update: (TaskWithCategoryTitle) -> Unit, private val archive: (TaskWithCategoryTitle) -> Unit) : RecyclerView.Adapter<TabTaskViewHolder>() {
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabTaskViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TabTaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TabTaskViewHolder, position: Int) {
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
}