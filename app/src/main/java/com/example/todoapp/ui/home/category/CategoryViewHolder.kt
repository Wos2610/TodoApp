package com.example.todoapp.ui.home.category

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemCategoryBinding
import com.example.todoapp.model.Category
import com.example.todoapp.model.CategoryWithTasks

class CategoryViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemCategoryBinding.bind(view)

    fun bind(category : CategoryWithTasks){
        binding.apply {
            categoryTitle.text = category.category.title
            completedPercentageText.text = category.category.completedPercentage.toString() + "%"
            completedPercentageProgressIndicator.progress = category.category.completedPercentage as Int * 100
        }
    }
}