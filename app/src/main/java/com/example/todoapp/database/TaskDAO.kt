package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.todoapp.model.CategoryWithTasks
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskWithCategoryTitle

@Dao
interface TaskDAO {
    @Query("SELECT t.*, c.categoryTitle FROM TASK_TABLE t INNER JOIN CATEGORY_TABLE c ON t.categoryId = c.category_id ORDER BY taskId ASC")
    fun getAllTasks() : LiveData<List<TaskWithCategoryTitle>>
    @Insert
    suspend fun insertTask(task : Task)
    @Update
    suspend fun updateTask(task : Task)
    @Delete
    suspend fun deleteTask(task : Task)

    @Query("SELECT t.*, c.categoryTitle FROM TASK_TABLE t INNER JOIN CATEGORY_TABLE c ON t.categoryId = c.category_id WHERE status = :status AND isArchive = 0 ORDER BY taskId ASC")
    //@Query("SELECT * FROM TASK_TABLE WHERE status = :status AND isArchive = 0 ORDER BY task_id ASC")
    @Transaction
    fun getTasksByStatus(status : Int) : LiveData<List<TaskWithCategoryTitle>>

    @Query("SELECT t.*, c.categoryTitle FROM TASK_TABLE t INNER JOIN CATEGORY_TABLE c ON t.categoryId = c.category_id WHERE status = :status AND isArchive = 0 ORDER BY " +
            "CASE WHEN :isASC THEN taskTitle END ASC, " +
            "CASE WHEN NOT :isASC THEN taskTitle END DESC")
    @Transaction
    fun getTasksByStatusAndNameOrder(status : Int, isASC : Boolean) : LiveData<List<TaskWithCategoryTitle>>

    @Query("SELECT t.*, c.categoryTitle FROM TASK_TABLE t INNER JOIN CATEGORY_TABLE c ON t.categoryId = c.category_id WHERE status = :status AND isArchive = 0 ORDER BY " +
            "CASE WHEN :isASC THEN priority END ASC, " +
            "CASE WHEN NOT :isASC THEN priority END DESC")
    @Transaction
    fun getTasksByStatusAndPriorityOrder(status : Int, isASC : Boolean) : LiveData<List<TaskWithCategoryTitle>>

    @Query("SELECT t.*, c.categoryTitle FROM TASK_TABLE t INNER JOIN CATEGORY_TABLE c ON t.categoryId = c.category_id WHERE status = :status AND isArchive = 0 ORDER BY " +
            "CASE WHEN :isASC THEN dueDate END ASC, " +
            "CASE WHEN NOT :isASC THEN dueDate END DESC")
    @Transaction
    fun getTasksByStatusAndDateOrder(status : Int, isASC : Boolean) : LiveData<List<TaskWithCategoryTitle>>

    @Query("SELECT t.*, c.categoryTitle FROM TASK_TABLE t INNER JOIN CATEGORY_TABLE c ON t.categoryId = c.category_id WHERE dueDate = :date AND isArchive = 0")
    @Transaction
    fun getTasksByDate(date : String) : LiveData<List<TaskWithCategoryTitle>>

    @Query("SELECT t.*, c.categoryTitle FROM TASK_TABLE t INNER JOIN CATEGORY_TABLE c ON t.categoryId = c.category_id WHERE isArchive = 1")
    @Transaction
    fun getArchiveTasks() : LiveData<List<TaskWithCategoryTitle>>
}