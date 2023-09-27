package com.example.todoapp.ui.home.category

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemCategoryBinding
import com.example.todoapp.model.Category
import com.example.todoapp.model.CategoryWithTasks

class CategoryAdapter : RecyclerView.Adapter<CategoryViewHolder>() {
    private lateinit var itemBinding : ItemCategoryBinding
    var categories: List<CategoryWithTasks> = listOf()
        get() {
            return field
        }
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentCategory = categories[position]
        Log.d("zxr", currentCategory.category.toString() + " " + currentCategory.tasks.toString())
        currentCategory.category.completedPercentage = if (currentCategory.tasks.isNotEmpty()) {
            (currentCategory.tasks.count { it.status == 3 } * 100) / currentCategory.tasks.size
        } else 0
        Log.d("zxr", currentCategory.category.completedPercentage.toString())
        holder.bind(currentCategory)
    }
}