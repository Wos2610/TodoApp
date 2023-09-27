package com.example.todoapp.database

import android.app.Application
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskWithCategoryTitle

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
    fun getTasksByStatus(status: Int): LiveData<List<TaskWithCategoryTitle>> =
        taskDAO.getTasksByStatus(status)

    fun getTasksByStatusAndNameOrder(status: Int, isASC: Boolean): LiveData<List<TaskWithCategoryTitle>> =
        taskDAO.getTasksByStatusAndNameOrder(status, isASC)

    fun getTasksByStatusAndPriorityOrder(status: Int, isASC: Boolean): LiveData<List<TaskWithCategoryTitle>> =
        taskDAO.getTasksByStatusAndPriorityOrder(status, isASC)

    fun getTasksByStatusAndDateOrder(status: Int, isASC: Boolean): LiveData<List<TaskWithCategoryTitle>> =
        taskDAO.getTasksByStatusAndDateOrder(status, isASC)

    fun getTasksByDate(date: String): LiveData<List<TaskWithCategoryTitle>> =
        taskDAO.getTasksByDate(date)

    fun getArchiveTasks(): LiveData<List<TaskWithCategoryTitle>> =
        taskDAO.getArchiveTasks()

    fun getDoneTasksByCategoryId(categoryId : Int) : LiveData<List<Task>> =
        taskDAO.getDoneTasksByCategoryId(categoryId)
}