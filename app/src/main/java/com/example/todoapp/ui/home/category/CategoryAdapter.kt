package com.example.todoapp.ui.home.category

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.ItemCategoryBinding
import com.example.todoapp.model.CategoryWithTasks

class CategoryAdapter(private val context: Context,
                      private val delete : (CategoryWithTasks) -> Unit,
                      private val edit : (CategoryWithTasks) -> Unit,
                      private val view : (CategoryWithTasks) -> Unit) : RecyclerView.Adapter<CategoryViewHolder>() {
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
        return CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        itemBinding = ItemCategoryBinding.bind(holder.itemView)
        val currentCategory = categories[position]
        Log.d("zxr", currentCategory.category.toString() + " " + currentCategory.tasks.toString())
        val currentCategoryCompletedPercentage = if (currentCategory.tasks.isNotEmpty()) {
            (currentCategory.tasks.count { it.status == 3 } * 100) / currentCategory.tasks.size
        } else 0

        currentCategory.category.completedPercentage = if (currentCategory.tasks.isNotEmpty()) {
            (currentCategory.tasks.count { it.status == 3 } * 100) / currentCategory.tasks.size
        } else 0

        Log.d("zxr", currentCategory.category.completedPercentage.toString())
        holder.bind(currentCategory, currentCategoryCompletedPercentage)

        itemBinding.categoryItem.setOnClickListener{
            view(currentCategory)
        }
        itemBinding.buttonDeleteCategory.setOnClickListener{
            val categoryPopUpMenu = PopupMenu(context, it)
            categoryPopUpMenu.menuInflater.inflate(R.menu.category_pop_up_menu, categoryPopUpMenu.menu)
            categoryPopUpMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.edit -> {
                        edit(currentCategory)
                    }
                    R.id.delete -> {
                        delete(currentCategory)
                    }
                }
                true
            })
            categoryPopUpMenu.show()
        }

    }
}