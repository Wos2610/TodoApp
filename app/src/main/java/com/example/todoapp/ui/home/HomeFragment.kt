package com.example.todoapp.ui.home

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentHomeBinding
import com.example.todoapp.ui.home.category.CategoryAdapter
import com.example.todoapp.ui.home.todayTask.TodayTaskAdapter
import com.example.todoapp.viewModel.CategoryViewModel
import com.example.todoapp.viewModel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val taskViewModel: TaskViewModel by activityViewModels()
    private val categoryViewModel: CategoryViewModel by activityViewModels()
    private lateinit var todayTaskAdapter: TodayTaskAdapter
    private val dateFormat = SimpleDateFormat("MMM-dd-yyyy")
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        todayTaskAdapter = TodayTaskAdapter(
            view = { task ->
                // De thong tin trong EditTaskFragment duoc truyen vao tu tasks[position]
//            taskViewModel.setEditTask(task)
//            taskViewModel.setNewTaskStatus(task.status)
//            taskViewModel.setNewTaskPriority(task.priority)
//            taskViewModel.setUpdateTaskCallback { task ->
//                taskViewModel.updateTask(task)
//            }
//            findNavController().navigate(R.id.action_taskFragment_to_editTaskFragment)
                taskViewModel.setViewTask(task)
                findNavController().navigate(R.id.action_taskFragment_to_viewTaskFragment)
            },
            scaleUpAnimation = { view ->
                val scaleUpAnim = AnimationUtils.loadAnimation(context, R.anim.scale_up)
                view.startAnimation(scaleUpAnim)
            },
            scaleDownAnimation = { view ->
                val scaleDownAnim = AnimationUtils.loadAnimation(context, R.anim.scale_down)
                view.startAnimation(scaleDownAnim)
            }
        )

        categoryAdapter = CategoryAdapter(requireContext(),
            delete = { category ->
                categoryViewModel.apply {
                    val newCategory = category.category
                    delete(newCategory)
                }
            },
            edit = { category ->
                categoryViewModel.apply {
                    setEditCategory(category)
//                    val newCategory = category.category
//                    update(newCategory)
                }
                findNavController().navigate(R.id.action_allCategoriesFragment_to_editCategoryFragment)
            },
            view = { category ->
                categoryViewModel.setViewCategory(category)
                findNavController().navigate(R.id.action_allCategoriesFragment_to_categoryWithListTasksFragment)
            }
        )

        binding.apply {
            todayTaskListRecyclerView.adapter = todayTaskAdapter
            todayTaskListRecyclerView.layoutManager = LinearLayoutManager(context)
            categoryRecyclerView.adapter = categoryAdapter
            categoryRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

//        binding.searchView.setupWithSearchBar(binding.searchBar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        taskViewModel.apply {
            setTodayListTasks(dateFormat.format(Calendar.getInstance().time))
            todayListTasks.observe(viewLifecycleOwner) { tasks ->
                tasks.let {
                    todayTaskAdapter.tasks = it
                    if(todayTaskAdapter.tasks.isEmpty()){
                        binding.apply {
                            noTaskTextView.visibility = View.VISIBLE
                            todayTaskListRecyclerView.visibility = View.GONE
                        }
                    }
                    else{
                        binding.apply {
                            noTaskTextView.visibility = View.GONE
                            todayTaskListRecyclerView.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }

        categoryViewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            categories.let { categoryAdapter.categories = it }
        }

        binding.floatingSearchView.apply {
            setOnQueryChangeListener { oldQuery, newQuery ->
                if (oldQuery == "" && newQuery == "") {
                    binding.floatingSearchView.clearSuggestions()
                } else {
                    binding.floatingSearchView.showProgress()
                    binding.floatingSearchView.swapSuggestions(getSuggestion(newQuery))
                    binding.floatingSearchView.hideProgress()
                }
            }

            setOnFocusChangeListener(object : FloatingSearchView.OnFocusChangeListener {
                override fun onFocus() {
                    binding.floatingSearchView.showProgress()
                    binding.floatingSearchView.swapSuggestions(getSuggestion(binding.floatingSearchView.query))
                    binding.floatingSearchView.hideProgress()
                }

                override fun onFocusCleared() {
                    binding.floatingSearchView.clearSuggestions()

                }
            })

            setOnSearchListener(object :
                FloatingSearchView.OnSearchListener {
                override fun onSuggestionClicked(searchSuggestion: SearchSuggestion) {
                    taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
                        tasks.forEach {
                            if (TaskSearchSuggestion(it.taskTitle).body == searchSuggestion.body) {
                                taskViewModel.setViewTask(it)
                                findNavController().navigate(R.id.action_homeFragment_to_viewTaskFragment)
                            }
                        }
                    }

                }

                override fun onSearchAction(currentQuery: String?) {
                    var isHaveTask = false
                    taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
                        tasks.forEach {
                            if (it.taskTitle.contains(currentQuery.toString(), true)) {
                                taskViewModel.setViewTask(it)
                                isHaveTask = true
                                findNavController().navigate(R.id.action_homeFragment_to_viewTaskFragment)
                            }
                        }
                        if(!isHaveTask){
                            Toast.makeText(context, "No task found", Toast.LENGTH_SHORT).show()
                            binding.floatingSearchView.clearFocus()
                        }
                    }
                }
            })
        }

        binding.apply {
            categoryViewModel.allCategories.observe(viewLifecycleOwner) { categories ->
                categoryNumber.text = categories.size.toString()
            }
            taskViewModel.todayListTasks.observe(viewLifecycleOwner) { tasks ->
                taskNumber.text = tasks.size.toString()
            }

            taskViewAll.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_allTasksFragment)
            }

            categoryViewAll.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_allCategoriesFragment)
            }
        }
    }

    private fun getSuggestion(query: String): List<SearchSuggestion> {
        val list = mutableListOf<SearchSuggestion>()
        taskViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            tasks.forEach {
                if (it.taskTitle.contains(query, true)) {
                    val suggestion = TaskSearchSuggestion(it.taskTitle)
                    list.add(suggestion)
                }
            }
        }
        return list
    }
}

class TaskSearchSuggestion(private val suggestion: String) : SearchSuggestion {
    constructor(parcel: Parcel) : this(parcel.readString()!!) {
    }

    override fun getBody(): String = suggestion
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(suggestion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskSearchSuggestion> {
        override fun createFromParcel(parcel: Parcel): TaskSearchSuggestion {
            return TaskSearchSuggestion(parcel)
        }

        override fun newArray(size: Int): Array<TaskSearchSuggestion?> {
            return arrayOfNulls(size)
        }
    }

}