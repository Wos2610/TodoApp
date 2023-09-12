package com.example.todoapp.ui.task
import DoneTabTaskFragment
import OnProgressTabTaskFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todoapp.ui.task.tabTask.TodoTabTaskFragment

class TaskAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fragmentManager, lifecycle){
    private val fragments = listOf(
        TodoTabTaskFragment(),
        OnProgressTabTaskFragment(),
        DoneTabTaskFragment()
    )
    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int) = fragments[position]
}