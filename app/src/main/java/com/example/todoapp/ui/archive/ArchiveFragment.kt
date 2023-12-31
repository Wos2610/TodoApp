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
import com.example.todoapp.model.Task
import com.example.todoapp.ui.archive.ArchiveTask.ArchiveTaskAdapter
import com.example.todoapp.viewModel.TaskViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ArchiveFragment : Fragment() {
    private lateinit var binding: FragmentArchiveBinding
    private lateinit var adapter: ArchiveTaskAdapter
    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArchiveBinding.inflate(inflater, container, false)
        adapter = ArchiveTaskAdapter(
            delete = { task ->
                val builder = MaterialAlertDialogBuilder(requireContext())
                builder.setTitle(getString(R.string.confirm))
                    .setMessage(getString(R.string.delete_button_message))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        taskViewModel.apply {
                            deleteTask(task)
                        }
                    }
                    .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            },
            restore = { task ->
                taskViewModel.apply {
                    val newTask = Task(
                        task.id,
                        task.title,
                        task.dueDate,
                        task.timeStart,
                        task.timeEnd,
                        task.categoryId,
                        task.status,
                        task.priority,
                        task.description,
                        false
                    )
                    updateTask(newTask)
                }
            }
        )
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
                tasks.let {
                    adapter.tasks = it
                    if(adapter.tasks.isEmpty()){
                        binding.apply {
                            noTaskTextView.visibility = View.VISIBLE
                            archiveTaskListRecyclerView.visibility = View.GONE
                        }
                    }
                    else{
                        binding.apply {
                            noTaskTextView.visibility = View.GONE
                            archiveTaskListRecyclerView.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

}