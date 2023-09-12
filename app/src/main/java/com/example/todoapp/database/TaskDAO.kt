package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.model.Task

@Dao
interface TaskDAO {
    @Query("SELECT * FROM TASK_TABLE ORDER BY id ASC")
    fun getAllTasks() : LiveData<List<Task>>
    @Insert
    suspend fun insertTask(task : Task)
    @Update
    suspend fun updateTask(task : Task)
    @Delete
    suspend fun deleteTask(task : Task)

    @Query("SELECT * FROM TASK_TABLE WHERE status = :status ORDER BY id ASC")
    fun getTasksByStatus(status : Int) : LiveData<List<Task>>

    @Query("SELECT * FROM TASK_TABLE WHERE status = :status ORDER BY " +
            "CASE WHEN :isASC THEN title END ASC, " +
            "CASE WHEN NOT :isASC THEN title END DESC")
    fun getTasksByStatusAndNameOrder(status : Int, isASC : Boolean) : LiveData<List<Task>>

    @Query("SELECT * FROM TASK_TABLE WHERE status = :status ORDER BY " +
            "CASE WHEN :isASC THEN priority END ASC, " +
            "CASE WHEN NOT :isASC THEN priority END DESC")
    fun getTasksByStatusAndPriorityOrder(status : Int, isASC : Boolean) : LiveData<List<Task>>

    @Query("SELECT * FROM TASK_TABLE WHERE status = :status ORDER BY " +
            "CASE WHEN :isASC THEN dueDate END ASC, " +
            "CASE WHEN NOT :isASC THEN dueDate END DESC")
    fun getTasksByStatusAndDateOrder(status : Int, isASC : Boolean) : LiveData<List<Task>>

    @Query("SELECT * FROM TASK_TABLE WHERE dueDate = :date")
    fun getTasksByDate(date : String) : LiveData<List<Task>>
}