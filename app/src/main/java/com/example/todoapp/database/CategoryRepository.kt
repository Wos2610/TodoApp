package com.example.todoapp.database

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.todoapp.model.Category
import com.example.todoapp.model.CategoryWithTasks

class CategoryRepository(context: Application) {
    companion object {
        private var INSTANCE: CategoryRepository? = null
        fun getInstance(context: Application): CategoryRepository {
            if (INSTANCE != null) {
                return INSTANCE!!
            }
            INSTANCE = CategoryRepository(context)
            return INSTANCE!!
        }
    }

    private val taskDatabase = TaskRoomDatabase.getDatabase(context)
    private val categoryDAO = taskDatabase.categoryDAO()
    val allCategory : LiveData<List<CategoryWithTasks>> = categoryDAO.getAllCategories()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertCategory(category: Category) = categoryDAO.insertCategory(category)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateCategory(category: Category) = categoryDAO.updateCategory(category)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteCategory(category: Category) = categoryDAO.deleteCategory(category)

    fun getCategoryById(id : Int) = categoryDAO.getCategoryById(id)

    suspend fun getCategoryTitleByTaskId(taskId: Int) = categoryDAO.getCategoryTitleByTaskId(taskId)
}