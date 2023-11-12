package com.example.todoapp.ui.home.category

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemCategoryBinding
import com.example.todoapp.model.CategoryWithTasks

class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
//    private val binding = ItemCategoryBinding.bind(view)

    fun bind(category : CategoryWithTasks, completedPercentage : Int){
        binding.apply {
            categoryTitle.text = category.category.title
            taskNum.text = "${category.tasks.size} ${itemView.context.getString(R.string.tasks)}"
            completedPercentageText.text = "$completedPercentage%"

            completedPercentageProgressIndicator.progress = completedPercentage
            Glide.with(itemView.context).load(category.category.imageName).into(categoryImage)
            //categoryImage.setImageURI(Uri.parse(category.category.imageName))
            Log.d("zxr", category.category.title)
            Log.d("zxr", category.category.imageName)

        }
    }
}