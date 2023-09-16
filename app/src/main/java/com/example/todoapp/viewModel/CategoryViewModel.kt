package com.example.todoapp.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.database.CategoryRepository
import com.example.todoapp.model.Category

class CategoryViewModel : ViewModel() {
    private val categoryRepository : CategoryRepository
    val allCategories : LiveData<List<Category>>

    init {
        categoryRepository = CategoryRepository.getInstance(context = Application())
        allCategories = categoryRepository.allCategory
    }
}