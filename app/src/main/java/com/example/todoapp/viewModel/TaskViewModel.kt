package com.example.todoapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.TaskRepository
import com.example.todoapp.model.Task
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class TaskViewModel(context : Application) : AndroidViewModel(context){
    private val taskRepository : TaskRepository
    val allTasks : LiveData<List<Task>>
    var listTasksByStatus : LiveData<List<Task>>
    var listTasksByStatusAndNameOrder : LiveData<List<Task>>
    var listTasksByStatusAndPriorityOrder : LiveData<List<Task>>
    var listTasksByStatusAndDateOrder : LiveData<List<Task>>
    var todayListTasks : LiveData<List<Task>>
    private val _insertTaskCallback = MutableLiveData<(Task) -> Unit>()
    private val _updateTaskCallback = MutableLiveData<(Task) -> Unit>()
    private lateinit var _editTask : Task
    private var _newTaskStatus : MutableLiveData<Int> = MutableLiveData(1)
    private var _newTaskPriority : MutableLiveData<Int> = MutableLiveData(1)

    fun setListTasksByStatus(status: Int) {
        listTasksByStatus = taskRepository.getTasksByStatus(status)
    }
    fun setListTasksByStatusAndNameOrder(status: Int, isASC : Boolean) {
        listTasksByStatusAndNameOrder = taskRepository.getTasksByStatusAndNameOrder(status, isASC)
    }

    fun setListTasksByStatusAndPriorityOrder(status: Int, isASC : Boolean) {
        listTasksByStatusAndPriorityOrder = taskRepository.getTasksByStatusAndPriorityOrder(status, isASC)
    }

    fun setListTasksByStatusAndDateOrder(status: Int, isASC : Boolean) {
        listTasksByStatusAndDateOrder = taskRepository.getTasksByStatusAndDateOrder(status, isASC)
    }

    fun setTodayListTasks(todayDate: String) {
        todayListTasks = taskRepository.getTasksByDate(todayDate)
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


    init{
        taskRepository = TaskRepository.getInstance(context)
        allTasks = taskRepository.allTask
        listTasksByStatus = taskRepository.getTasksByStatus(1)
        listTasksByStatusAndNameOrder = taskRepository.getTasksByStatusAndNameOrder(1, false)
        listTasksByStatusAndPriorityOrder = taskRepository.getTasksByStatusAndPriorityOrder(1, false)
        listTasksByStatusAndDateOrder = taskRepository.getTasksByStatusAndDateOrder(1, false)
        val dateFormat = SimpleDateFormat("MMM-dd-yyyy")
        todayListTasks = taskRepository.getTasksByDate(dateFormat.format(Calendar.getInstance().time))
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