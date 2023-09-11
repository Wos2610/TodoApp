package com.example.todoapp.ui.task

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.TaskRepository
import com.example.todoapp.database.TaskRoomDatabase
import com.example.todoapp.model.Task
import kotlinx.coroutines.launch

class TaskViewModel(context : Application) : AndroidViewModel(context){
    private val taskRepository : TaskRepository
    val allTasks : LiveData<List<Task>>
    private val _insertTaskCallback = MutableLiveData<(Task) -> Unit>()
    private val _updateTaskCallback = MutableLiveData<(Task) -> Unit>()
    private lateinit var _editTask : Task
    private var _newTaskStatus : MutableLiveData<Int> = MutableLiveData(0)
    private var _newTaskPriority : MutableLiveData<Int> = MutableLiveData(0)

    val newTaskStatus : LiveData<Int>
        get() = _newTaskStatus

    fun setNewTaskStatus(status : Int){
        _newTaskStatus.value = status
    }

    val newTaskPriority : LiveData<Int>
        get() = _newTaskPriority

    fun setNewTaskPriority(priority : Int){
        _newTaskPriority.value = priority
    }
    val insertTaskCallback: LiveData<(Task) -> Unit>
        get() = _insertTaskCallback

    fun setInsertTaskCallback(callback: (Task) -> Unit) {
        _insertTaskCallback.value = callback
    }

    val updateTaskCallback: LiveData<(Task) -> Unit>
        get() = _updateTaskCallback

    fun setUpdateTaskCallback(callback: (Task) -> Unit) {
        _updateTaskCallback.value = callback
    }

    val editTask: Task
        get() = _editTask

    fun setEditTask(task: Task) {
        _editTask = task
    }

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