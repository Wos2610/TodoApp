package com.example.todoapp.ui.task.editTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListPopupWindow
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentEditTaskBinding
import com.example.todoapp.model.Category
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskWithCategoryTitle
import com.example.todoapp.ui.task.tabTask.ListPopupWindowAdapter
import com.example.todoapp.viewModel.CategoryViewModel
import com.example.todoapp.viewModel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class EditTaskFragment : Fragment() {
    private lateinit var binding: FragmentEditTaskBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    private val categoryViewModel: CategoryViewModel by activityViewModels()
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
            backButton.setOnClickListener{
                parentFragmentManager.popBackStack()
            }

            taskViewModel.editTask?.let {
                nameEditText.setText(it.taskTitle)
                dateTextView.text = it.dueDate
                startTimeTextView.text = it.timeStart
                endTimeTextView.text = it.timeEnd
                categoryViewModel.getCategoryById(it.categoryId).observe(viewLifecycleOwner) { category ->
                    if (category != null) {
                        // Handle the category data here
                        categoryTextView.text = category.category.title
                    } else {
                        // Handle the case where the category is null (not found)
                        categoryTextView.text = "Category Not Found"
                    }
                }
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
                        val selectedDate = Calendar.getInstance()
                        selectedDate.set(selectedYear, selectedMonth, selectedDay)
                        dateTextView.text = dateFormat.format(selectedDate.time)
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.setTitle("Select Date")
                datePickerDialog.show()
            })

            startTimeTextView.setOnClickListener(View.OnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val minute: Int = calendar.get(Calendar.MINUTE)
                val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)

                val timePickerDialog = TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        startTimeTextView.setText(String.format("%02d:%02d", hourOfDay, minute))
                    },
                    hour,
                    minute,
                    true
                )
                timePickerDialog.setTitle("Select Start Time")
                timePickerDialog.show()
            })

            endTimeTextView.setOnClickListener(View.OnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val minute: Int = calendar.get(Calendar.MINUTE)
                val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)

                val timePickerDialog = TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        endTimeTextView.setText(String.format("%02d:%02d", hourOfDay, minute))
                    },
                    hour,
                    minute,
                    true
                )
                timePickerDialog.setTitle("Select End Time")
                timePickerDialog.show()
            })

            categoryTextView.setOnClickListener {
                showListPopupWindow(it)
            }

            changeButton.setOnClickListener {
                val newTask = TaskWithCategoryTitle(
                    taskViewModel.editTask.taskId,
                    nameEditText.text.toString(),
                    dateTextView.text.toString(),
                    startTimeTextView.text.toString(),
                    endTimeTextView.text.toString(),
                    taskViewModel.newTaskCategoryId.value!!,
                    //category_id from 1 but index (allCategories) from 0
                    categoryViewModel.allCategories.value?.get(taskViewModel.newTaskCategoryId.value!!.minus(1))?.category?.title.toString(),
                    taskViewModel.newTaskStatus.value!!,
                    taskViewModel.newTaskPriority.value!!,
                    descriptionEditText.text.toString(),
                    false,
                )

                taskViewModel.setEditTask(newTask)
                taskViewModel.setViewTask(newTask)
                taskViewModel.updateTaskCallback.observe(viewLifecycleOwner) { callback ->
                    val task = Task(
                        taskViewModel.editTask.taskId,
                        taskViewModel.editTask.taskTitle,
                        taskViewModel.editTask.dueDate,
                        taskViewModel.editTask.timeStart,
                        taskViewModel.editTask.timeEnd,
                        taskViewModel.editTask.categoryId,
                        taskViewModel.editTask.status,
                        taskViewModel.editTask.priority,
                        taskViewModel.editTask.description,
                        taskViewModel.editTask.isArchive
                    )
                    callback.invoke(
                        task
                    )
                }
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun changeStatusAndPriority(){
        taskViewModel.newTaskStatus.observe(viewLifecycleOwner){ status ->
//            binding.statusTextView.text = status.toString()

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
//            binding.priorityTextView.text = priority.toString()

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

    private fun showListPopupWindow(anchorView: View) {
        val listPopupWindow = ListPopupWindow(anchorView.context)
        listPopupWindow.anchorView = anchorView
        listPopupWindow.width = 500
        val listPopupWindowAdapter = ListPopupWindowAdapter(
            categoryViewModel.allCategories, activity,
            callback = { position ->
                taskViewModel.setNewTaskCategoryId(position + 1)
                taskViewModel.newTaskCategoryId.observe(viewLifecycleOwner) { id ->
                    binding.categoryTextView.text = categoryViewModel.allCategories.value?.get(id - 1)?.category?.title.toString()
                }
                listPopupWindow.dismiss()
            }
        )

        listPopupWindow.setAdapter(listPopupWindowAdapter)
        categoryViewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            // Update the adapter's data when LiveData changes
            listPopupWindowAdapter.notifyDataSetChanged()
        }
        listPopupWindow.show()
    }
}