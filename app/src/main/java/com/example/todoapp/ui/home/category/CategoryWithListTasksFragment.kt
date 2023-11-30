package com.example.todoapp.ui.home.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentCategoryWithListTasksBinding
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskWithCategoryTitle
import com.example.todoapp.viewModel.CategoryViewModel
import com.example.todoapp.viewModel.TaskViewModel
import kotlinx.coroutines.launch

class CategoryWithListTasksFragment : Fragment() {
    private lateinit var binding : FragmentCategoryWithListTasksBinding
    private lateinit var taskAdapter : TaskAdapter
    private val categoryViewModel : CategoryViewModel by activityViewModels()
    private val taskViewModel : TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding  = FragmentCategoryWithListTasksBinding.inflate(inflater, container, false)
        taskAdapter = TaskAdapter(
            view = { task ->
                // De thong tin trong EditTaskFragment duoc truyen vao tu tasks[position]
//            taskViewModel.setEditTask(task)
//            taskViewModel.setNewTaskStatus(task.status)
//            taskViewModel.setNewTaskPriority(task.priority)
//            taskViewModel.setUpdateTaskCallback { task ->
//                taskViewModel.updateTask(task)
//            }
//            findNavController().navigate(R.id.action_taskFragment_to_editTaskFragment)
                val newTask = TaskWithCategoryTitle(
                    task.id,
                    task.title,
                    task.dueDate,
                    task.timeStart,
                    task.timeEnd,
                    task.categoryId,
                    categoryViewModel.viewCategory.category.title,
                    task.status,
                    task.priority,
                    task.description,
                    task.isArchive
                )
                taskViewModel.apply {
                    setEditTask(newTask)
                    setNewTaskStatus(newTask.status)
                    setNewTaskPriority(newTask.priority)
                    setNewTaskCategoryId(newTask.categoryId)
                    // Why use updateTaskCallback: Because updatedTask is changed in EditTaskFragment
                    setUpdateTaskCallback { task ->
                        taskViewModel.updateTask(task)
                    }
                }
//                taskViewModel.setViewTask(newTask)
                findNavController().navigate(R.id.action_taskFragment_to_editTaskFragment)
            },
            categoryTitle = categoryViewModel.viewCategory.category.title,
        )
        binding.apply {
            taskListRecyclerView.layoutManager = LinearLayoutManager(context)
            taskListRecyclerView.adapter = taskAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launch {
            categoryViewModel.getCategoryWithListTasksById(categoryViewModel.viewCategory.category.id).collect{
                taskAdapter.tasks = it.tasks
                if(taskAdapter.tasks.isEmpty()){
                    binding.apply {
                        noTaskTextView.visibility = View.VISIBLE
                        taskListRecyclerView.visibility = View.GONE
                    }
                }
                else{
                    binding.apply {
                        noTaskTextView.visibility = View.GONE
                        taskListRecyclerView.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.apply {
            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            categoryTitleTextView.text = categoryViewModel.viewCategory.category.title.uppercase()

            addTaskButton.setOnClickListener {
                // default : status = 1, priority = 1
                taskViewModel.setNewTaskStatus(1)
                taskViewModel.setNewTaskPriority(1)
                // create new task in this category -> default category
                taskViewModel.setNewTaskCategoryId(categoryViewModel.viewCategory.category.id)
                taskViewModel.setIsCertainCategory(true)
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
        }
    }

}