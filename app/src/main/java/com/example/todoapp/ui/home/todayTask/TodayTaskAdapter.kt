package com.example.todoapp.ui.home.todayTask

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTodayTaskBinding
import com.example.todoapp.model.TaskWithCategoryTitle
import com.example.todoapp.viewModel.CategoryViewModel

class TodayTaskAdapter(private val scaleUpAnimation : (View) -> Unit,
                       private val scaleDownAnimation : (View) -> Unit,
                       private val edit : (TaskWithCategoryTitle) -> Unit) : RecyclerView.Adapter<TodayTaskViewHolder>() {
    private lateinit var itemBinding : ItemTodayTaskBinding
    var tasks: List<TaskWithCategoryTitle> = listOf()
        get() {
            return field
        }
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayTaskViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_today_task, parent, false)
        return TodayTaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: TodayTaskViewHolder, position: Int) {
        itemBinding = ItemTodayTaskBinding.bind(holder.itemView)
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
        holder.itemView.setOnClickListener{
            edit(currentTask)
        }
    }
}