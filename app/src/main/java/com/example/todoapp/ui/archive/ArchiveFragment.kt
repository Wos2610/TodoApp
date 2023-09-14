package com.example.todoapp.ui.archive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentArchiveBinding
import com.example.todoapp.ui.archive.ArchiveTask.ArchiveTaskAdapter
import com.example.todoapp.viewModel.TaskViewModel

class ArchiveFragment : Fragment() {
    private lateinit var binding : FragmentArchiveBinding
    private lateinit var adapter: ArchiveTaskAdapter
    private val taskViewModel : TaskViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArchiveBinding.inflate(inflater, container, false)
        adapter = ArchiveTaskAdapter()
        binding.apply {
            archiveTaskListRecyclerView.adapter = adapter
            archiveTaskListRecyclerView.layoutManager = LinearLayoutManager(context)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        taskViewModel.apply {
            setArchiveListTasks()
            archiveListTasks.observe(viewLifecycleOwner) { tasks ->
                tasks.let { adapter.tasks = it }
            }
        }
    }

}