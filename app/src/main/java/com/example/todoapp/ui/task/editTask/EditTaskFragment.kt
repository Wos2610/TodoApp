package com.example.todoapp.ui.task.editTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentEditTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.viewModel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class EditTaskFragment : Fragment() {
    private lateinit var binding: FragmentEditTaskBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        changeStatusAndPriority()
        binding.apply {
            taskViewModel.editTask?.let {
                nameEditText.setText(it.title)
                dateTextView.text = it.dueDate
                startTimeEditText.setText(it.timeStart)
                endTimeEditText.setText(it.timeEnd)
                categoryTextView.text = it.categoryId.toString()
                priorityTextView.text = it.priority.toString()
                statusTextView.text = it.status.toString()
                descriptionEditText.setText(it.description)
            }

            dateTextView.setOnClickListener(View.OnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val year: Int = calendar.get(Calendar.YEAR)
                val month: Int = calendar.get(Calendar.MONTH)
                val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

                val monthNames = arrayOf(
                    "January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
                )

                val dateFormat = SimpleDateFormat("MMM-dd-yyyy")
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { _, selectedYear, selectedMonth, selectedDay->
                        dateTextView.text = dateFormat.format(calendar.time)
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.setTitle("Select Date")
                datePickerDialog.show()
            })

            startTimeEditText.setOnClickListener(View.OnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val minute: Int = calendar.get(Calendar.MINUTE)
                val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)

                val timePickerDialog = TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        startTimeEditText.setText(String.format("%02d:%02d", hourOfDay, minute))
                    },
                    hour,
                    minute,
                    true
                )
                timePickerDialog.setTitle("Select Start Time")
                timePickerDialog.show()
            })

            endTimeEditText.setOnClickListener(View.OnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val minute: Int = calendar.get(Calendar.MINUTE)
                val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)

                val timePickerDialog = TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        endTimeEditText.setText(String.format("%02d:%02d", hourOfDay, minute))
                    },
                    hour,
                    minute,
                    true
                )
                timePickerDialog.setTitle("Select End Time")
                timePickerDialog.show()
            })

            changeButton.setOnClickListener {
                taskViewModel.setEditTask(
                    Task(
                        taskViewModel.editTask.id,
                        nameEditText.text.toString(),
                        dateTextView.text.toString(),
                        startTimeEditText.text.toString(),
                        endTimeEditText.text.toString(),
                        1,
                        taskViewModel.newTaskStatus.value!!,
                        taskViewModel.newTaskPriority.value!!,
                        descriptionEditText.text.toString(),
                        false
                    )
                )

                taskViewModel.updateTaskCallback.observe(viewLifecycleOwner) { callback ->
                    callback.invoke(
                        taskViewModel.editTask
                    )
                }
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun changeStatusAndPriority(){
        taskViewModel.newTaskStatus.observe(viewLifecycleOwner){ status ->
            binding.statusTextView.text = status.toString()

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
        }

        taskViewModel.newTaskPriority.observe(viewLifecycleOwner){ priority ->
            binding.priorityTextView.text = priority.toString()

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
}