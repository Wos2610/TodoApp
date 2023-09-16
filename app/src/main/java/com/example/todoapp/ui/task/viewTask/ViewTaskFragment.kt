package com.example.todoapp.ui.task.viewTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentViewTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.viewModel.TaskViewModel

class ViewTaskFragment : Fragment() {
    private lateinit var binding : FragmentViewTaskBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    private lateinit var task : Task
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        task = taskViewModel.viewTask
        binding.apply {
            nameEditText.text = task.title
            dateTextView.text = task.dueDate
            startTimeEditText.text = task.timeStart
            endTimeEditText.text = task.timeEnd
            categoryTextView.text = task.categoryId.toString()
            changeStatusAndPriority(task.status, task.priority)
            descriptionEditText.text = task.description
        }

        binding.editButton.setOnClickListener{
            taskViewModel.apply {
                setEditTask(task)
                setNewTaskStatus(task.status)
                setNewTaskPriority(task.priority)
                setUpdateTaskCallback { task ->
                    taskViewModel.updateTask(task)
                }
            }
            findNavController().navigate(R.id.action_viewFragment_to_editTaskFragment)
        }

        binding.backButton.setOnClickListener{
            parentFragmentManager.popBackStack()
        }
    }

    private fun changeStatusAndPriority(status : Int, priority : Int){
        when(status){
            1 -> {
                binding.todoTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    )
                )
                binding.onProgressTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                binding.doneTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            }

            2 -> {
                binding.todoTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                binding.onProgressTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    )
                )
                binding.doneTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            }

            3 -> {
                binding.todoTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                binding.onProgressTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                binding.doneTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    )
                )
            }
        }

        when(priority){
            1 -> {
                binding.lowTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    )
                )
                binding.midTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                binding.highTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            }

            2 -> {
                binding.lowTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                binding.midTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    )
                )
                binding.highTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            }

            3 -> {
                binding.lowTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                binding.midTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                binding.highTextView.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.blue
                    )
                )
            }
        }

        binding.apply {
            todoTextView.setOnClickListener {
                taskViewModel.setNewTaskStatus(1)
            }

            onProgressTextView.setOnClickListener {
                taskViewModel.setNewTaskStatus(2)
            }

            doneTextView.setOnClickListener {
                taskViewModel.setNewTaskStatus(3)
            }

            lowTextView.setOnClickListener {
                taskViewModel.setNewTaskPriority(1)
            }

            midTextView.setOnClickListener {
                taskViewModel.setNewTaskPriority(2)
            }

            highTextView.setOnClickListener {
                taskViewModel.setNewTaskPriority(3)
            }
        }
    }

}