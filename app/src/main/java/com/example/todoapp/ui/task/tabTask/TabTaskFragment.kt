package com.example.todoapp.ui.task.tabTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentDetailTabBinding
import com.example.todoapp.model.Task
import com.example.todoapp.viewModel.TaskViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class TabTaskFragment(private val status : Int) : Fragment() {
    private lateinit var taskBinding: FragmentDetailTabBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    private lateinit var taskAdapter: TabTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        taskBinding = FragmentDetailTabBinding.inflate(inflater, container, false)
        taskAdapter = TabTaskAdapter(
            edit = { task ->
                // De thong tin trong EditTaskFragment duoc truyen vao tu tasks[position]
                taskViewModel.apply {
                    setEditTask(task)
                    setNewTaskStatus(task.status)
                    setNewTaskPriority(task.priority)
                    setNewTaskCategoryId(task.categoryId)
                    // Why use updateTaskCallback: Because updatedTask is changed in EditTaskFragment
                    setUpdateTaskCallback { task ->
                        taskViewModel.updateTask(task)
                    }
                }
                findNavController().navigate(R.id.action_taskFragment_to_editTaskFragment)
//                taskViewModel.setViewTask(task)
//                findNavController().navigate(R.id.action_taskFragment_to_viewTaskFragment)
            },
            archive = { task ->
                val builder = MaterialAlertDialogBuilder(requireContext())
                builder.setTitle(getString(R.string.confirm))
                    .setMessage(getString(R.string.archive_button_message))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        taskViewModel.apply {
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
                        }
                    }
                    .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            },
            scaleUpAnimation = { view ->
                val scaleUpAnim = AnimationUtils.loadAnimation(context, R.anim.scale_up)
                view.startAnimation(scaleUpAnim)
            },
            scaleDownAnimation = { view ->
                val scaleDownAnim = AnimationUtils.loadAnimation(context, R.anim.scale_down)
                view.startAnimation(scaleDownAnim)
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
                tasks.let {
                    taskAdapter.tasks = it
                    if(taskAdapter.tasks.isEmpty()){
                        taskBinding.apply {
                            noTaskTextView.visibility = View.VISIBLE
                            taskListRecyclerView.visibility = View.GONE
                        }
                    }
                    else{
                        taskBinding.apply {
                            noTaskTextView.visibility = View.GONE
                            taskListRecyclerView.visibility = View.VISIBLE
                        }
                    }
                }
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