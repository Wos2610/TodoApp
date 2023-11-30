package com.example.todoapp.ui.task.newTask

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListPopupWindow
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.databinding.ItemCategoryListPopupWindowBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.task.tabTask.ListPopupWindowAdapter
import com.example.todoapp.viewModel.CategoryViewModel
import com.example.todoapp.viewModel.TaskViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


class NewTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    private val categoryViewModel: CategoryViewModel by activityViewModels()

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
            val currentCalendar = Calendar.getInstance()
            dateTextView.text = SimpleDateFormat("MMM-dd-yyyy", Locale.getDefault()).format(currentCalendar.time)
            startTimeTextView.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(currentCalendar.time)
            currentCalendar.add(Calendar.HOUR_OF_DAY, 1)
            endTimeTextView.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(currentCalendar.time)

            backButton.setOnClickListener{
                val builder = MaterialAlertDialogBuilder(requireContext())
                builder.setTitle(getString(R.string.confirm))
                    .setMessage(getString(R.string.back_button_message))
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        parentFragmentManager.popBackStack()
                    }
                    .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            }
            dateTextView.setOnClickListener(View.OnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val dateFormat = SimpleDateFormat("MMM-dd-yyyy")

                try {
                    // Parse the date from dateTextView to initialize the DatePickerDialog
                    val initialDate = dateFormat.parse(dateTextView.text.toString())
                    calendar.time = initialDate
                } catch (e: ParseException) {
                    // Handle the parse exception if the dateTextView doesn't contain a valid date
                    e.printStackTrace()
                }

                val year: Int = calendar.get(Calendar.YEAR)
                val month: Int = calendar.get(Calendar.MONTH)
                val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

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
                val formatter = SimpleDateFormat("HH:mm")
                var minu: Int
                var hour: Int

                try {
                    // Parse the time from startTimeTextView to initialize the TimePickerDialog
                    val initialTime = formatter.parse(startTimeTextView.text.toString())
                    calendar.time = initialTime!!
                    hour = calendar.get(Calendar.HOUR_OF_DAY)
                    minu = calendar.get(Calendar.MINUTE)
                } catch (e: ParseException) {
                    // Handle the parse exception if the startTimeTextView doesn't contain a valid time
                    val currentTime = Calendar.getInstance()
                    hour = currentTime.get(Calendar.HOUR_OF_DAY)
                    minu = currentTime.get(Calendar.MINUTE)
                    e.printStackTrace()
                }

                val timePickerDialog = TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        startTimeTextView.text = String.format("%02d:%02d", hourOfDay, minute)
                    },
                    hour,
                    minu,
                    true
                )
                timePickerDialog.setTitle("Select Start Time")
                timePickerDialog.show()
            })

            endTimeTextView.setOnClickListener(View.OnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val formatter = SimpleDateFormat("HH:mm")
                var minu: Int
                var hour: Int

                try {
                    // Parse the time from startTimeTextView to initialize the TimePickerDialog
                    val initialTime = formatter.parse(endTimeTextView.text.toString())
                    calendar.time = initialTime!!
                    hour = calendar.get(Calendar.HOUR_OF_DAY)
                    minu = calendar.get(Calendar.MINUTE)
                } catch (e: ParseException) {
                    // Handle the parse exception if the startTimeTextView doesn't contain a valid time
                    val currentTime = Calendar.getInstance()
                    hour = currentTime.get(Calendar.HOUR_OF_DAY)
                    minu = currentTime.get(Calendar.MINUTE)
                    e.printStackTrace()
                }

                val timePickerDialog = TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        endTimeTextView.text = String.format("%02d:%02d", hourOfDay, minute)
                    },
                    hour,
                    minu,
                    true
                )
                timePickerDialog.setTitle("Select End Time")
                timePickerDialog.show()
            })

            categoryLayout.setOnClickListener {
                showListPopupWindow(it)
            }

            taskViewModel.isCertainCategory.observe(viewLifecycleOwner) { isCertainCategory ->
                if(isCertainCategory){
                    taskViewModel.newTaskCategoryId.observe(viewLifecycleOwner) { id ->
                        // categoryViewModel.getCategoryById(id) return Flow<Category>
                        lifecycleScope.launch {
                            categoryViewModel.getCategoryById(id).collect{
                                binding.categoryTextView.text = it.title
                            }
                        }
                    }
                }
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

                val formatter = DateTimeFormatter.ofPattern("HH:mm")
                if(startTimeTextView.text.toString() == ""){
                    startTimeTextView.error = getString(R.string.start_time_error)
                    return@setOnClickListener
                }
                else{
                    startTimeTextView.error = null
                }
                if(endTimeTextView.text.toString() == ""){
                    endTimeTextView.error = getString(R.string.end_time_error)
                    return@setOnClickListener
                }
                else{
                    endTimeTextView.error = null
                }

                try {
                    val startTime = LocalTime.parse(startTimeTextView.text.toString(), formatter)
                    val endTime = LocalTime.parse(endTimeTextView.text.toString(), formatter)
                    if(startTime.isAfter(endTime)){
                        startTimeTextView.error = getString(R.string.error_time)
                        endTimeTextView.error = getString(R.string.error_time)
                        return@setOnClickListener
                    }
                    else{
                        startTimeTextView.error = null
                        endTimeTextView.error = null
                    }
                }
                catch (e: Exception){
                    startTimeTextView.error = getString(R.string.error_time)
                    endTimeTextView.error = getString(R.string.error_time)
                    return@setOnClickListener
                    e.printStackTrace()
                }

                if(categoryTextView.text.toString() == ""){
                    categoryTextView.error = getString(R.string.category_name_error)
                    return@setOnClickListener
                }
                else{
                    categoryTextView.error = null
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
        val typedValue = TypedValue()
        requireContext().theme.resolveAttribute(R.attr.textColor, typedValue, true)
        val color = typedValue.resourceId
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
                            color
                        )
                    )
                    binding.doneTextView.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            color
                        )
                    )
                }

                2 -> {
                    binding.todoTextView.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            color
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
                            color
                        )
                    )
                }

                3 -> {
                    binding.todoTextView.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            color
                        )
                    )
                    binding.onProgressTextView.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            color
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
                            color
                        )
                    )
                    binding.highTextView.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            color
                        )
                    )
                }

                2 -> {
                    binding.lowTextView.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            color
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
                            color
                        )
                    )
                }

                3 -> {
                    binding.lowTextView.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            color
                        )
                    )
                    binding.midTextView.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            color
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
//

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
        categoryViewModel.allCategories.observe(viewLifecycleOwner) {
            // Update the adapter's data when LiveData changes
            listPopupWindowAdapter.notifyDataSetChanged()
        }
        listPopupWindow.show()
    }

}

