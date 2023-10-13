package com.example.todoapp.ui.home.category

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemCategoryBinding
import com.example.todoapp.model.Category
import com.example.todoapp.model.CategoryWithTasks

class CategoryViewHolder(view : View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemCategoryBinding.bind(view)

    fun bind(category : CategoryWithTasks, completedPercentage : Int){
        binding.apply {
            categoryTitle.text = category.category.title
            taskNum.text = category.tasks.size.toString() + " " + itemView.context.getString(R.string.tasks)
            completedPercentageText.text = "$completedPercentage%"
            Log.d("zxr", category.category.completedPercentage.toString())
            completedPercentageProgressIndicator.progress = completedPercentage
            categoryImage.load(Uri.parse(category.category.imageName))
        }
    }
}