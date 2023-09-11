package com.example.todoapp.ui.task

import TaskAdapter
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
import com.example.todoapp.ui.task.tabTask.DetailTabFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TaskFragment : Fragment() {
    private lateinit var taskBinding: FragmentTaskBinding
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        taskBinding = FragmentTaskBinding.inflate(inflater, container, false)
//        taskAdapter = DetailTaskAdapter(
//            update = { task ->
//                // De thong tin trong EditTaskFragment duoc truyen vao tu tasks[position]
//                taskViewModel.setEditTask(task)
//                taskViewModel.setNewTaskStatus(task.status)
//                taskViewModel.setNewTaskPriority(task.priority)
//                taskViewModel.setUpdateTaskCallback {task ->
//                    taskViewModel.updateTask(task)
//                }
//                findNavController().navigate(R.id.action_taskFragment_to_editTaskFragment)
//            },
//            delete = { task ->
//                taskViewModel.deleteTask(task)
//            }
//        )
//        taskBinding.apply {
//            taskListRecyclerView.layoutManager = LinearLayoutManager(context)
//            taskListRecyclerView.adapter = taskAdapter
//        }
        return taskBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskBinding = FragmentTaskBinding.bind(view)
//        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
//            tasks.let { taskAdapter.tasks = it }
//        }
//
//        taskBinding.addTaskButton.setOnClickListener {
//            taskViewModel.setInsertTaskCallback {task : Task ->
//                taskViewModel.insertTask(task)
//            }
//            findNavController().navigate(R.id.action_taskFragment_to_newTaskFragment)
//        }
        val fragments = listOf(
            DetailTabFragment.newInstance(StatusType.TODO.value, StatusType.TODO.description),
            DetailTabFragment.newInstance(
                StatusType.ON_PROGRESS.value,
                StatusType.ON_PROGRESS.description
            ),
            DetailTabFragment.newInstance(StatusType.DONE.value, StatusType.DONE.description)
        )
        taskAdapter = TaskAdapter(childFragmentManager, lifecycle, fragments)
        tabLayout = taskBinding.tabLayout
        viewPager = taskBinding.viewPager2
        viewPager.adapter = taskAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = StatusType.values()[position + 1].description
        }.attach()
    }
}
