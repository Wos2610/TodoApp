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

class AllTaskAdapter : RecyclerView.Adapter<AllTaskAdapter.AllTaskViewHolder>() {
    var tasks: List<Task> = listOf()
        get() {
            return field
        }
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class AllTaskViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val itemBinding : ItemTaskBinding = ItemTaskBinding.bind(view)
        fun bind(task : Task){
            itemBinding.apply {
                itemTitle.text = task.title
            }
        }
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
        Log.d("abcde", "onBindViewHolder")
    }


}