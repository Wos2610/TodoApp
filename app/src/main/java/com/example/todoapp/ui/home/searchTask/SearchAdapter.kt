package com.example.todoapp.ui.home.searchTask

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemTodayTaskBinding
import com.example.todoapp.model.Task

class SearchAdapter : RecyclerView.Adapter<SearchViewHolder>() {
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_today_task, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

    }
}