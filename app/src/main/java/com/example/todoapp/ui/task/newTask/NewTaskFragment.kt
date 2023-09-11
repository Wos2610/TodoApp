package com.example.todoapp.ui.task.newTask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.todoapp.databinding.FragmentAddTaskBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.task.TaskViewModel
import java.util.Calendar


//class NewTaskFragment(private val insert : (Task) -> Unit) : Fragment() {
class NewTaskFragment : Fragment() {
    private lateinit var binding : FragmentAddTaskBinding
    private var newTaskPriority  = 0
    private var newTaskStatus  = 0
    private  val taskViewModel: TaskViewModel by activityViewModels()
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
        binding.apply {
            dateTextView.setOnClickListener(View.OnClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val year: Int = calendar.get(Calendar.YEAR)
                val month: Int = calendar.get(Calendar.MONTH)
                val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

                val monthNames = arrayOf(
                    "January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
                )

                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { _, selectedYear, selectedMonth, selectedDay->
                        dateTextView.text = "${monthNames[selectedMonth]} $selectedDay $selectedYear"
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
                    requireContext(),
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
                    requireContext(),
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

            todoTextView.setOnClickListener{
                newTaskStatus = 1
            }

            onProgressTextView.setOnClickListener{
                newTaskStatus = 2
            }

            doneTextView.setOnClickListener{
                newTaskStatus = 3
            }

            lowTextView.setOnClickListener{
                newTaskPriority = 1
            }

            midTextView.setOnClickListener{
                newTaskPriority = 2
            }

            highTextView.setOnClickListener{
                newTaskPriority = 3
            }

            creatButton.setOnClickListener {
                taskViewModel.insertTaskCallback.observe(viewLifecycleOwner) { callback ->
                    callback.invoke(Task(
                        0,
                        nameEditText.text.toString(),
                        dateTextView.text.toString(),
                        startTimeEditText.text.toString(),
                        endTimeEditText.text.toString(),
                        1,
                        newTaskStatus,
                        newTaskPriority,
                        descriptionEditText.text.toString()
                    ))
                }
                parentFragmentManager.popBackStack()
            }
        }
    }
}