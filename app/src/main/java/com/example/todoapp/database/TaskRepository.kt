package com.example.todoapp.database

import android.app.Application
import androidx.annotation.WorkerThread
import com.example.todoapp.model.Task

class TaskRepository(context : Application){
    companion object {
        private var INSTANCE: TaskRepository? = null
        fun getInstance(context: Application): TaskRepository {
            if (INSTANCE != null) {
                return INSTANCE!!
            }
            INSTANCE = TaskRepository(context)
            return INSTANCE!!
        }
    }

    private val taskDatabase = TaskRoomDatabase.getDatabase(context)
    private val taskDAO = taskDatabase.taskDAO()
    val allTask = taskDAO.getAllTasks()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertTask(task: Task) = taskDAO.insertTask(task)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateTask(task: Task) = taskDAO.updateTask(task)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteTask(task: Task) = taskDAO.deleteTask(task)
    fun getTasksByStatus(status : Int) = taskDAO.getTasksByStatus(status)
    fun getTasksByStatusAndNameOrder(status : Int, isASC : Boolean) = taskDAO.getTasksByStatusAndNameOrder(status, isASC)
    fun getTasksByStatusAndPriorityOrder(status : Int, isASC : Boolean) = taskDAO.getTasksByStatusAndPriorityOrder(status, isASC)
    fun getTasksByStatusAndDateOrder(status : Int, isASC : Boolean) = taskDAO.getTasksByStatusAndDateOrder(status, isASC)
    fun getTasksByDate(date : String) = taskDAO.getTasksByDate(date)
}