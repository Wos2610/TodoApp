package com.example.todoapp.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.CategoryRepository
import com.example.todoapp.model.Category
import com.example.todoapp.model.CategoryWithTasks
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val categoryRepository : CategoryRepository = CategoryRepository.getInstance(context = Application())
    val allCategories : LiveData<List<CategoryWithTasks>> = categoryRepository.allCategory

    fun insert(category: Category){
        viewModelScope.launch {
            categoryRepository.insertCategory(category)
        }
    }
    fun update(category: Category){
        viewModelScope.launch {
            categoryRepository.updateCategory(category)
        }
    }
    fun delete(category: Category){
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
        }
    }

    fun getCategoryById(id : Int) : LiveData<CategoryWithTasks>{
        return categoryRepository.getCategoryById(id)
    }

    suspend fun getCategoryTitleByTaskId(taskId: Int) : String?{
        return categoryRepository.getCategoryTitleByTaskId(taskId)
    }
}