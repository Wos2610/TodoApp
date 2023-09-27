package com.example.todoapp.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.CategoryRepository
import com.example.todoapp.model.Category
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val categoryRepository : CategoryRepository
    val allCategories : LiveData<List<Category>>

    init {
        categoryRepository = CategoryRepository.getInstance(context = Application())
        allCategories = categoryRepository.allCategory
    }
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

    fun getCategoryById(id : Int) : LiveData<Category>{
        return categoryRepository.getCategoryById(id)
    }

    suspend fun getCategoryTitleByTaskId(taskId: Int) : String?{
        return categoryRepository.getCategoryTitleByTaskId(taskId)
    }
}