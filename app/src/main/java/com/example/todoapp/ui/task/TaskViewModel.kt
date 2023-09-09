package com.example.todoapp.ui.task

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.TaskRepository
import com.example.todoapp.database.TaskRoomDatabase
import com.example.todoapp.model.Task
import kotlinx.coroutines.launch

class TaskViewModel(context : Application) : AndroidViewModel(context){
    private val taskRepository : TaskRepository
    val allTasks : LiveData<List<Task>>

    init{
        taskRepository = TaskRepository.getInstance(context)
        allTasks = taskRepository.allTask
    }

    fun insertTask(task : Task){
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }

    fun updateTask(task : Task){
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun deleteTask(task : Task){
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

}