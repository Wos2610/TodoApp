package com.example.todoapp.viewModel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.CategoryRepository
import com.example.todoapp.model.Category
import com.example.todoapp.model.CategoryWithTasks
import com.example.todoapp.model.TaskWithCategoryTitle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    private val categoryRepository: CategoryRepository =
        CategoryRepository.getInstance(context = Application())
    val allCategories: LiveData<List<CategoryWithTasks>> = categoryRepository.allCategory
    private val _insertCategoryCallback = MutableLiveData<(Category) -> Unit>()
    private val _editCategoryCallback = MutableLiveData<(Category) -> Unit>()
    private lateinit var _editCategory: CategoryWithTasks
    private lateinit var _viewCategory: CategoryWithTasks
    private val _imageUri = MutableLiveData<Uri>()

    fun insert(category: Category) {
        viewModelScope.launch {
            categoryRepository.insertCategory(category)
        }
    }

    fun update(category: Category) {
        viewModelScope.launch {
            categoryRepository.updateCategory(category)
        }
    }

    fun delete(category: Category) {
        viewModelScope.launch {
            categoryRepository.deleteCategory(category)
        }
    }
    fun getCategoryById(id: Int): Flow<Category> {
        return categoryRepository.getCategoryById(id)
    }

    fun getCategoryWithListTasksById(id: Int): Flow<CategoryWithTasks> {
        return categoryRepository.getCategoryWithListTasksById(id)
    }
    fun setInsertCategoryCallback(callback: (Category) -> Unit) {
        _insertCategoryCallback.value = callback
    }
    val insertCategoryCallback: LiveData<(Category) -> Unit>
        get() = _insertCategoryCallback
    fun setEditCategoryCallback(callback: (Category) -> Unit) {
        _editCategoryCallback.value = callback
    }
    val editCategoryCallback: LiveData<(Category) -> Unit>
        get() = _editCategoryCallback

    val editCategory : CategoryWithTasks
        get() = _editCategory

    fun setEditCategory(category: CategoryWithTasks) {
        _editCategory = category
    }

    val viewCategory : CategoryWithTasks
        get() = _viewCategory

    fun setViewCategory(category: CategoryWithTasks) {
        _viewCategory = category
    }

    val imageUri: LiveData<Uri>
        get() = _imageUri

    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }


    suspend fun getCategoryTitleByTaskId(taskId: Int): String? {
        return categoryRepository.getCategoryTitleByTaskId(taskId)
    }

    fun getCategoryIdByTitle(name: String): Int? {
        return categoryRepository.getCategoryIdByTitle(name)
    }
}