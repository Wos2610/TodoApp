package com.example.todoapp.ui.home.category

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.ItemCategoryBinding
import com.example.todoapp.model.Category

class CategoryViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemCategoryBinding.bind(view)

    fun bind(category : Category){
        binding.apply {
            categoryTitle.text = category.title
        }
    }
}