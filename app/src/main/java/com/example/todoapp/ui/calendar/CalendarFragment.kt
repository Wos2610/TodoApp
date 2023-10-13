package com.example.todoapp.ui.calendar

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentCalendarBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import java.time.LocalDate
import java.time.YearMonth

class CalendarFragment : Fragment() {
    private lateinit var binding: FragmentCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    //    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.calendarView.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                container.binding.calendarDayText.text = data.date.dayOfMonth.toString()
            }

            override fun create(view: View) = DayViewContainer(view)
        }
//        val currentMonth = YearMonth.now()
//        val startMonth = currentMonth.minusMonths(100)
//        val endMonth = currentMonth.plusMonths(100)
//        val firstDayOfWeek = firstDayOfWeekFromLocale()
//        binding.calendarView.apply {
//            setup(startMonth, endMonth, firstDayOfWeek)
//            scrollToMonth(currentMonth)
//        }
//
//        val currentDate = LocalDate.now()
//        val startDate = currentMonth.minusMonths(100).atStartOfMonth() // Adjust as needed
//        val endDate = currentMonth.plusMonths(100).atEndOfMonth()  // Adjust as needed
//        binding.apply {
//            weekCalendarView.setup(startDate, endDate, firstDayOfWeek)
//            weekCalendarView.scrollToWeek(currentDate)
//        }
    }

}