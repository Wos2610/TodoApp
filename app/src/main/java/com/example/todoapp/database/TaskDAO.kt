package com.example.todoapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {
    @Query("SELECT * FROM TASK_TABLE ORDER BY id ASC")
    fun getAllTask() : LiveData<List<Task>>
    @Insert
    suspend fun insertTask(task : Task)
    @Update
    suspend fun updateTask(task : Task)
    @Delete
    suspend fun deleteTask(task : Task)
}