package com.example.todoapp.ui.home.allTasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAllTasksBinding
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskWithCategoryTitle
import com.example.todoapp.ui.task.tabTask.TabTaskAdapter
import com.example.todoapp.viewModel.CategoryViewModel
import com.example.todoapp.viewModel.TaskViewModel

class AllTasksFragment : Fragment() {
    private lateinit var taskBinding: FragmentAllTasksBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    private val categoryViewModel: CategoryViewModel by activityViewModels()
    private lateinit var allTasksAdapter: TabTaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        taskBinding = FragmentAllTasksBinding.inflate(inflater, container, false)
        allTasksAdapter = TabTaskAdapter(
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
            taskListRecyclerView.adapter = allTasksAdapter
        }
        return taskBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        taskViewModel.apply {
            allTasks.observe(viewLifecycleOwner) { tasks ->
                tasks.let {
                    allTasksAdapter.tasks = it
                    if(allTasksAdapter.tasks.isEmpty()){
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

        taskBinding.addTaskButton.setOnClickListener {
            // default : status = 1, priority = 1
            taskViewModel.setNewTaskStatus(1)
            taskViewModel.setNewTaskPriority(1)
            taskViewModel.setIsCertainCategory(false)
            taskViewModel.setInsertTaskCallback {task : Task ->
                taskViewModel.insertTask(task)
                val newTask = TaskWithCategoryTitle(
                    task.id,
                    task.title,
                    task.dueDate,
                    task.timeStart,
                    task.timeEnd,
                    task.categoryId,
                    categoryViewModel.getCategoryById(task.categoryId).toString(),
                    task.status,
                    task.priority,
                    task.description,
                    task.isArchive
                )
                taskViewModel.setViewTask(newTask)
            }
            findNavController().navigate(R.id.action_taskFragment_to_newTaskFragment)
        }

        taskBinding.backButton.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
    }

}