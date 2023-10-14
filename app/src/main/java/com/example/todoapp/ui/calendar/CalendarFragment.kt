package com.example.todoapp.ui.calendar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.CalendarDayLayoutBinding
import com.example.todoapp.databinding.CalendarHeaderBinding
import com.example.todoapp.databinding.FragmentCalendarBinding
import com.example.todoapp.model.TaskWithCategoryTitle
import com.example.todoapp.viewModel.TaskViewModel
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    private lateinit var taskCalendarAdapter: TaskCalendarAdapter
    private val taskViewModel: TaskViewModel by activityViewModels()
    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    private val events: MutableMap<LocalDate, List<TaskWithCategoryTitle>> = mutableMapOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        taskCalendarAdapter = TaskCalendarAdapter(
            view = { task ->
                taskViewModel.setViewTask(task)
                findNavController().navigate(R.id.action_taskFragment_to_viewTaskFragment)
            }
        )

        binding.apply {
            taskListRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            taskListRecyclerView.adapter = taskCalendarAdapter
        }
        return binding.root
    }

    //    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            if (selectedDate == null) {
                selectedDate = today
            }

            val filteredTasks = tasks.filter { task ->
                task.dueDate == selectedDate!!.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"))
            }

            Log.d("Debug", "Filtered tasks before sorting: $filteredTasks")
            val sortedTasks = filteredTasks.sortedBy { it.timeStart }
            Log.d("Debug", "Sorted tasks: $sortedTasks")

            // Set sorted tasks in the adapter.
            taskCalendarAdapter.tasks = sortedTasks
            taskCalendarAdapter.notifyDataSetChanged()
        }

        binding.calendarView.monthScrollListener = {
            binding.exFiveMonthYearText.text = it.yearMonth.month.toString()
            selectDate(it.yearMonth.atDay(1))
        }

        binding.exFiveNextMonthImage.setOnClickListener {
            binding.calendarView.findFirstVisibleMonth()?.let {
                binding.calendarView.smoothScrollToMonth(it.yearMonth.nextMonth)
            }
        }

        binding.exFivePreviousMonthImage.setOnClickListener {
            binding.calendarView.findFirstVisibleMonth()?.let {
                binding.calendarView.smoothScrollToMonth(it.yearMonth.previousMonth)
            }
        }

        val daysOfWeek = daysOfWeek()
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(50)
        val endMonth = currentMonth.plusMonths(50)
        configureBinders(daysOfWeek)
        binding.calendarView.apply {
            setup(startMonth, endMonth, daysOfWeek.first())
            scrollToMonth(currentMonth)
        }

        if (savedInstanceState == null) {
            // Show today's events initially.
            binding.calendarView.post { selectDate(today) }
        }
    }

    fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { binding.calendarView.notifyDateChanged(it) }
            binding.calendarView.notifyDateChanged(date)
            updateAdapterForDate(date)
        }
    }

    private fun updateAdapterForDate(date: LocalDate) {
        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            tasks.let {
                val filteredTasks = tasks.filter { task ->
                    task.dueDate == selectedDate!!.format(DateTimeFormatter.ofPattern("MMM-dd-yyyy"))
                }

                Log.d("Debug", "Filtered tasks before sorting: $filteredTasks")
                val sortedTasks = filteredTasks.sortedBy { it.timeStart }
                Log.d("Debug", "Sorted tasks: $sortedTasks")

                // Set sorted tasks in the adapter.
                taskCalendarAdapter.tasks = sortedTasks
                taskCalendarAdapter.notifyDataSetChanged()
                events.clear()
                events[date] = taskCalendarAdapter.tasks

                val formatter = DateTimeFormatter.ofPattern("MMM-dd-yyyy")
                for (task in it) {
                    val eachDate = LocalDate.parse(task.dueDate, formatter)
                    if (events.containsKey(eachDate)) {
                        val list = events[eachDate]!!.toMutableList()
                        list.add(task)
                        events[eachDate] = list
                    } else {
                        events[eachDate] = listOf(task)
                    }
                }
                binding.calendarView.notifyCalendarChanged()
            }
        }
//        binding.exThreeSelectedDateText.text = selectionFormatter.format(date)
    }

    private fun configureBinders(daysOfWeek: List<DayOfWeek>) {
        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val dayBinding = CalendarDayLayoutBinding.bind(view)

            init {
                view.setOnClickListener {
                    if (day.position == DayPosition.MonthDate) {
                        selectDate(day.date)
                    }
                }
            }
        }

        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.day = data
                val textView = container.dayBinding.dayText
                val dotView = container.dayBinding.dotView

                textView.text = data.date.dayOfMonth.toString()

                if (data.position == DayPosition.MonthDate) {
                    textView.visibility = View.VISIBLE
                    when (data.date) {
                        today -> {
                            textView.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.white
                                )
                            )
                            textView.setBackgroundResource(R.drawable.background_today)
                            dotView.visibility = View.INVISIBLE
                        }

                        selectedDate -> {
                            textView.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.blue
                                )
                            )
                            textView.setBackgroundResource(R.drawable.background_selected_day)
                            dotView.visibility = View.INVISIBLE
                        }

                        else -> {
                            textView.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            textView.background = null
                            if (events.containsKey(data.date)) {
                                dotView.visibility = View.VISIBLE
                            } else {
                                dotView.visibility = View.INVISIBLE
                            }
                        }
                    }
                } else {
                    textView.visibility = View.INVISIBLE
                    dotView.visibility = View.INVISIBLE
                }
            }
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = CalendarHeaderBinding.bind(view).legendLayout.root
        }
        binding.calendarView.monthHeaderBinder =
            object : MonthHeaderFooterBinder<MonthViewContainer> {
                override fun create(view: View) = MonthViewContainer(view)
                override fun bind(container: MonthViewContainer, data: CalendarMonth) {
                    // Setup each header day text if we have not done that already.
                    if (container.legendLayout.tag == null) {
                        container.legendLayout.tag = data.yearMonth
                        container.legendLayout.children.map { it as TextView }
                            .forEachIndexed { index, tv ->
                                tv.text = daysOfWeek[index].name.take(2)
                                tv.setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.black
                                    )
                                )
                            }
                    }
                }
            }
    }
}