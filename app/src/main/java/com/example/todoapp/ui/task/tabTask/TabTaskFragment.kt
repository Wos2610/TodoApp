package com.example.todoapp.ui.task.tabTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentDetailTabBinding
import com.example.todoapp.viewModel.TaskViewModel

abstract class TabTaskFragment(private val status : Int) : Fragment() {
    private lateinit var taskBinding: FragmentDetailTabBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    private lateinit var taskAdapter: TabTaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        taskBinding = FragmentDetailTabBinding.inflate(inflater, container, false)
        taskAdapter = TabTaskAdapter(
            update = { task ->
                // De thong tin trong EditTaskFragment duoc truyen vao tu tasks[position]
                taskViewModel.apply {
                    setEditTask(task)
                    setNewTaskStatus(task.status)
                    setNewTaskPriority(task.priority)
                    setUpdateTaskCallback { task ->
                        taskViewModel.updateTask(task)
                    }
                }
                findNavController().navigate(R.id.action_taskFragment_to_editTaskFragment)
            },
            archive = { task ->
                taskViewModel.apply {
                    setEditTask(task)
                    setNewTaskIsArchive(true)
                    setUpdateTaskCallback { task ->
                        taskViewModel.updateTask(task)
                    }
                }
            }
        )

        taskBinding.apply {
            taskListRecyclerView.layoutManager = LinearLayoutManager(context)
            taskListRecyclerView.adapter = taskAdapter
        }
        return taskBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskBinding = FragmentDetailTabBinding.bind(view)

        taskViewModel.apply {
            setListTasksByStatus(status)
            listTasksByStatus.observe(viewLifecycleOwner) { tasks ->
                tasks.let { taskAdapter.tasks = it }
            }
        }

        taskBinding.sortTaskButton.setOnClickListener { it ->
            val sortPopUpMenu = PopupMenu(activity, it)
            sortPopUpMenu.menuInflater.inflate(R.menu.sort_pop_up_menu, sortPopUpMenu.menu)
            sortPopUpMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.name_ascending -> {
                        taskViewModel.apply {
                            setListTasksByStatusAndNameOrder(status, true)
                            listTasksByStatusAndNameOrder.observe(viewLifecycleOwner) { tasks ->
                                tasks.let { taskAdapter.tasks = it }
                            }
                        }
                    }

                    R.id.name_descending -> {
                        taskViewModel.apply {
                            setListTasksByStatusAndNameOrder(status, false)
                            listTasksByStatusAndNameOrder.observe(viewLifecycleOwner) { tasks ->
                                tasks.let { taskAdapter.tasks = it }
                            }
                        }
                    }

                    R.id.priority_ascending -> {
                        taskViewModel.apply {
                            setListTasksByStatusAndPriorityOrder(status, true)
                            listTasksByStatusAndPriorityOrder.observe(viewLifecycleOwner) { tasks ->
                                tasks.let { taskAdapter.tasks = it }
                            }
                        }
                    }

                    R.id.priority_descending -> {
                        taskViewModel.apply {
                            setListTasksByStatusAndPriorityOrder(status, false)
                            listTasksByStatusAndPriorityOrder.observe(viewLifecycleOwner) { tasks ->
                                tasks.let { taskAdapter.tasks = it }
                            }
                        }
                    }


                    R.id.date_ascending -> {
                        taskViewModel.apply {
                            setListTasksByStatusAndDateOrder(status, true)
                            listTasksByStatusAndDateOrder.observe(viewLifecycleOwner) { tasks ->
                                tasks.let { taskAdapter.tasks = it }
                            }
                        }
                    }

                    R.id.date_descending -> {
                        taskViewModel.apply {
                            setListTasksByStatusAndDateOrder(status, false)
                            listTasksByStatusAndDateOrder.observe(viewLifecycleOwner) { tasks ->
                                tasks.let { taskAdapter.tasks = it }
                            }
                        }
                    }
                }
                true
            })
            sortPopUpMenu.show()
        }
    }
}