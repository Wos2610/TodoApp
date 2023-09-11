package com.example.todoapp.ui.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentTaskBinding
import com.example.todoapp.databinding.ItemTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.task.editTask.EditTaskFragment
import com.example.todoapp.ui.task.newTask.NewTaskFragment

class TaskFragment : Fragment() {
    private lateinit var taskBinding : FragmentTaskBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    private lateinit var allTaskAdapter: AllTaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        taskBinding = FragmentTaskBinding.inflate(inflater, container, false)
        allTaskAdapter = AllTaskAdapter(
            update = { task ->
                // De thong tin trong EditTaskFragment duoc truyen vao
                taskViewModel.setEditTask(task)
                taskViewModel.setNewTaskStatus(task.status)
                taskViewModel.setNewTaskPriority(task.priority)
                taskViewModel.setUpdateTaskCallback {task ->
                    taskViewModel.updateTask(task)
                }
                findNavController().navigate(R.id.action_taskFragment_to_editTaskFragment)
            },
            delete = { task ->
                taskViewModel.deleteTask(task)
            }
        )
        taskBinding.apply {
            taskListRecyclerView.layoutManager = LinearLayoutManager(context)
            taskListRecyclerView.adapter = allTaskAdapter
        }
        return  taskBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskBinding = FragmentTaskBinding.bind(view)
        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            tasks.let { allTaskAdapter.tasks = it }
        }

        taskBinding.addTaskButton.setOnClickListener {
            taskViewModel.setInsertTaskCallback {task : Task ->
                taskViewModel.insertTask(task)
            }
            findNavController().navigate(R.id.action_taskFragment_to_newTaskFragment)
        }

    }

}