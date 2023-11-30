package com.example.todoapp.ui.task.tabTask

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.TaskWithCategoryTitle

class TabTaskAdapter(private val edit: (TaskWithCategoryTitle) -> Unit,
                     private val archive: (TaskWithCategoryTitle) -> Unit,
                     private val scaleUpAnimation : (View) -> Unit,
                     private val scaleDownAnimation : (View) -> Unit) : RecyclerView.Adapter<TabTaskViewHolder>() {
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
//        holder.itemView.setOnTouchListener{v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_UP -> {
//                    scaleUpAnimation(v)
//                }
//                MotionEvent.ACTION_DOWN -> {
//                    scaleDownAnimation(v)
//                }
//            }
//            false
//
//        }
        itemBinding.dragItem.setOnClickListener{
            edit(currentTask)
        }
        itemBinding.archiveButton.setOnClickListener{
            archive(currentTask)
        }
    }
}