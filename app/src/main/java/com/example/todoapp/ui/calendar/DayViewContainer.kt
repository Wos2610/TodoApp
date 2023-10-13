package com.example.todoapp.ui.calendar

import android.view.View
import com.example.todoapp.databinding.CalendarDayLayoutBinding
import com.kizitonwose.calendar.view.ViewContainer

class DayViewContainer(view : View) :ViewContainer(view) {
    val binding = CalendarDayLayoutBinding.bind(view)

}