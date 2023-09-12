package com.example.todoapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentHomeBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.home.todayTask.TodayTaskAdapter
import com.example.todoapp.viewModel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private val taskViewModel : TaskViewModel by activityViewModels()
    private lateinit var todayTaskAdapter: TodayTaskAdapter
    private val dateFormat = SimpleDateFormat("MMM-dd-yyyy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        todayTaskAdapter = TodayTaskAdapter {
                task: Task ->
            // De thong tin trong EditTaskFragment duoc truyen vao tu tasks[position]
            taskViewModel.setEditTask(task)
            taskViewModel.setNewTaskStatus(task.status)
            taskViewModel.setNewTaskPriority(task.priority)
            taskViewModel.setUpdateTaskCallback { task ->
                taskViewModel.updateTask(task)
            }
            findNavController().navigate(R.id.action_taskFragment_to_editTaskFragment)
        }

        binding.apply {
            todayTaskListRecyclerView.adapter = todayTaskAdapter
            todayTaskListRecyclerView.layoutManager = LinearLayoutManager(context)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        taskViewModel.apply {
            setTodayListTasks(dateFormat.format(Calendar.getInstance().time))
            todayListTasks.observe(viewLifecycleOwner) { tasks ->
                tasks.let { todayTaskAdapter.tasks = it }
            }
        }
    }
}