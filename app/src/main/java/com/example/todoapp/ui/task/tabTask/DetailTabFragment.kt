package com.example.todoapp.ui.task.tabTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentDetailTabBinding
import com.example.todoapp.model.Task
import com.example.todoapp.ui.task.DetailTaskAdapter
import com.example.todoapp.ui.task.TaskViewModel

class DetailTabFragment(private val status : Int) : Fragment() {
    private var text = ""
    private lateinit var taskBinding : FragmentDetailTabBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    private lateinit var taskAdapter: DetailTaskAdapter
    companion object {
        @JvmStatic
        fun newInstance(status : Int, newText : String) =
            DetailTabFragment(status).apply {
                val args = Bundle()
                val fragment = DetailTabFragment(status)
                fragment.text = newText
                fragment.arguments = args;
                return fragment
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        taskBinding = FragmentDetailTabBinding.inflate(inflater, container, false)
        taskAdapter = DetailTaskAdapter(
            update = { task ->
                // De thong tin trong EditTaskFragment duoc truyen vao tu tasks[position]
                taskViewModel.setEditTask(task)
                taskViewModel.setNewTaskStatus(task.status)
                taskViewModel.setNewTaskPriority(task.priority)
                taskViewModel.setUpdateTaskCallback {task ->
                    taskViewModel.updateTask(task)
                }
                findNavController().navigate(R.id.action_taskFragment_to_editTaskFragment)
            },
            delete = { task ->
                taskViewModel.deleteTask(task)
            }
        )

        taskBinding.apply {
            taskListRecyclerView.layoutManager = LinearLayoutManager(context)
            taskListRecyclerView.adapter = taskAdapter
        }
        return  taskBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taskBinding = FragmentDetailTabBinding.bind(view)

        taskViewModel.setListTasksByStatus(status)
        taskViewModel.listTasksByStatus.observe(viewLifecycleOwner) { tasks ->
            tasks.let { taskAdapter.tasks = it }
        }

        taskBinding.addTaskButton.setOnClickListener {
            taskViewModel.setInsertTaskCallback {task : Task ->
                taskViewModel.insertTask(task)
            }
            findNavController().navigate(R.id.action_taskFragment_to_newTaskFragment)
        }
    }
}