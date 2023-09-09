package com.example.todoapp.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentTaskBinding
import com.example.todoapp.model.Task

class TaskFragment : Fragment() {
    private lateinit var binding : FragmentTaskBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private lateinit var allTaskAdapter: AllTaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        allTaskAdapter = AllTaskAdapter()
        binding.apply {
            taskListRecyclerView.adapter = allTaskAdapter
            taskListRecyclerView.layoutManager = LinearLayoutManager(context)
        }

        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            tasks.let { allTaskAdapter.tasks = it }
        }

        val task = Task(0, "Task 1", "11/02/2022", "11:00", "11:30", 1, 1, 1, "Description")
        taskViewModel.insertTask(task)
    }


}