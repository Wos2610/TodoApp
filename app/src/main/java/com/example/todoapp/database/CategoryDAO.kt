package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.model.Category
import com.example.todoapp.model.CategoryWithTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDAO {
    @Query("SELECT * FROM CATEGORY_TABLE ORDER BY category_id ASC")
    fun getAllCategories() : LiveData<List<CategoryWithTasks>>

    @Insert
    suspend fun insertCategory(category : Category)
    @Update
    suspend fun updateCategory(category : Category)
    @Delete
    suspend fun deleteCategory(category : Category)

    @Query("SELECT * FROM CATEGORY_TABLE WHERE category_id = :id")
    fun getCategoryById(id : Int) : Flow<Category>

    @Query("SELECT * FROM CATEGORY_TABLE WHERE category_id = :id")
    fun getCategoryWithListTasksById(id : Int) : Flow<CategoryWithTasks>

    @Query("SELECT category_id FROM CATEGORY_TABLE WHERE categoryTitle = :name")
    fun getCategoryIdByTitle(name : String) : Int?

    @Query("SELECT categoryTitle FROM CATEGORY_TABLE INNER JOIN TASK_TABLE ON categoryId = categoryId WHERE taskId = :taskId")
    suspend fun getCategoryTitleByTaskId(taskId: Int): String?


}