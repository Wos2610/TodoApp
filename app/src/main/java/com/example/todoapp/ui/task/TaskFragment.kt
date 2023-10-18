package com.example.todoapp.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskWithCategoryTitle
import com.example.todoapp.ui.task.enums.StatusType
import com.example.todoapp.viewModel.CategoryViewModel
import com.example.todoapp.viewModel.TaskViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TaskFragment : Fragment() {
    private lateinit var taskBinding: FragmentTaskBinding
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private val taskViewModel: TaskViewModel by activityViewModels()
    private val categoryViewModel: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        taskBinding = FragmentTaskBinding.inflate(inflater, container, false)
        return taskBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskBinding = FragmentTaskBinding.bind(view)
//        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
//            tasks.let { taskAdapter.tasks = it }
//        }

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

        taskAdapter = TaskAdapter(childFragmentManager, lifecycle)
        tabLayout = taskBinding.tabLayout
        viewPager = taskBinding.viewPager2
        viewPager.adapter = taskAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = StatusType.values()[position + 1].description
        }.attach()
    }

//    override fun onDestroyView() {
//        categoryViewModel.responseState.removeObservers(parentFragment.viewLifecycleOwner)
//        super.onDestroyView()
//    }
}