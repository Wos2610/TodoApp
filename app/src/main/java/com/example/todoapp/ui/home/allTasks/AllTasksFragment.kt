package com.example.todoapp.ui.home.allTasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAllTasksBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.task.tabTask.TabTaskAdapter
import com.example.todoapp.viewModel.TaskViewModel

class AllTasksFragment : Fragment() {
    private lateinit var taskBinding: FragmentAllTasksBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    private lateinit var allTasksAdapter: TabTaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskBinding = FragmentAllTasksBinding.inflate(inflater, container, false)
        allTasksAdapter = TabTaskAdapter(
            update = { task ->
                // De thong tin trong EditTaskFragment duoc truyen vao tu tasks[position]
//                taskViewModel.apply {
//                    setEditTask(task)
//                    setNewTaskStatus(task.status)
//                    setNewTaskPriority(task.priority)
//                    setUpdateTaskCallback { task ->
//                        taskViewModel.updateTask(task)
//                    }
//                }
                taskViewModel.setViewTask(task)
                findNavController().navigate(R.id.action_taskFragment_to_viewTaskFragment)
            },
            archive = { task ->
                taskViewModel.apply {
                    val newTask = Task(
                        task.taskId,
                        task.taskTitle,
                        task.dueDate,
                        task.timeStart,
                        task.timeEnd,
                        task.categoryId,
                        task.status,
                        task.priority,
                        task.description,
                        true
                    )
                    updateTask(newTask)
                }
            },
        )

        taskBinding.apply {
            taskListRecyclerView.layoutManager = LinearLayoutManager(context)
            taskListRecyclerView.adapter = allTasksAdapter
        }
        return taskBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        taskViewModel.apply {
            allTasks.observe(viewLifecycleOwner) { tasks ->
                tasks.let { allTasksAdapter.tasks = it }
            }
        }

        taskBinding.backButton.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
    }

}