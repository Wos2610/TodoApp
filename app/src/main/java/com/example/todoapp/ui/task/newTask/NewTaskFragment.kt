package com.example.todoapp.ui.task.newTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListPopupWindow
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskWithCategoryTitle
import com.example.todoapp.ui.task.tabTask.ListPopupWindowAdapter
import com.example.todoapp.viewModel.CategoryViewModel
import com.example.todoapp.viewModel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class NewTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    private val categoryViewModel: CategoryViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        changeStatusAndPriority()

        binding.apply {
            backButton.setOnClickListener{
                parentFragmentManager.popBackStack()
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
                    { _, selectedYear, selectedMonth, selectedDay ->
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
                        startTimeTextView.text = String.format("%02d:%02d", hourOfDay, minute)
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
                        endTimeTextView.text = String.format("%02d:%02d", hourOfDay, minute)
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

            createButton.setOnClickListener {
                if(nameEditText.text.toString() == ""){
                    nameEditText.error = getString(R.string.task_name_error)
                    return@setOnClickListener
                }
                else{
                    nameEditText.error = null
                }
                if(dateTextView.text.toString() == ""){
                    dateTextView.error = getString(R.string.task_date_error)
                    return@setOnClickListener
                }
                else{
                    dateTextView.error = null
                }
                taskViewModel.insertTaskCallback.observe(viewLifecycleOwner) { callback ->
                    callback.invoke(
                        Task(
                            0,
                            nameEditText.text.toString(),
                            dateTextView.text.toString(),
                            startTimeTextView.text.toString(),
                            endTimeTextView.text.toString(),
                            taskViewModel.newTaskCategoryId.value!!,
                            taskViewModel.newTaskStatus.value!!,
                            taskViewModel.newTaskPriority.value!!,
                            descriptionEditText.text.toString(),
                            false
                        )
                    )
                }
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun changeStatusAndPriority() {
        taskViewModel.newTaskStatus.observe(viewLifecycleOwner) { status ->
            //binding.statusTextView.text = status.toString()

            when (status) {
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

        taskViewModel.newTaskPriority.observe(viewLifecycleOwner) { priority ->
            //binding.priorityTextView.text = priority.toString()
            when (priority) {
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
                // position from 0 but category_id from 1
                val categoryId = categoryViewModel.allCategories.value?.get(position)?.category?.id
//                Log.d("zxr", categoryViewModel.getCategoryByName(categoryName!!).value)
//                taskViewModel.setNewTaskCategoryId(categoryViewModel.getCategoryIdByTitle(categoryName!!)!!)
//                taskViewModel.setNewTaskCategoryId(position + 1)
                Log.d("hihi", categoryId.toString())
                taskViewModel.setNewTaskCategoryId(categoryId!!)

                taskViewModel.newTaskCategoryId.observe(viewLifecycleOwner) { id ->
                    // categoryViewModel.getCategoryById(id) return Flow<Category>
                    lifecycleScope.launch {
                        categoryViewModel.getCategoryById(id).collect{
                            binding.categoryTextView.text = it.title
                        }
                    }
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

