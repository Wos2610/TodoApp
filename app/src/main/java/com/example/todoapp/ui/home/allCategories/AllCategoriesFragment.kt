package com.example.todoapp.ui.home.allCategories

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAllCategoriesBinding
import com.example.todoapp.model.Category
import com.example.todoapp.model.CategoryWithTasks
import com.example.todoapp.ui.home.category.CategoryAdapter
import com.example.todoapp.viewModel.CategoryViewModel

class AllCategoriesFragment : Fragment() {
    private lateinit var binding : FragmentAllCategoriesBinding
    private lateinit var allCategoriesAdapter: CategoryAdapter
    private val categoryViewModel : CategoryViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllCategoriesBinding.inflate(inflater, container, false)
        allCategoriesAdapter = CategoryAdapter(requireContext(),
            delete = { category ->
                categoryViewModel.apply {
                    val newCategory = category.category
                    delete(newCategory)
                }
            },
            edit = { category ->
                categoryViewModel.update(category.category)
                categoryViewModel.setEditCategory(category)
                findNavController().navigate(R.id.action_allCategoriesFragment_to_editCategoryFragment)
            },
            view = { category ->
                categoryViewModel.setViewCategory(category)
                findNavController().navigate(R.id.action_allCategoriesFragment_to_categoryWithListTasksFragment)
            }
        )
        binding.apply {
            categoryRecyclerView.layoutManager = GridLayoutManager(context, 2)
            categoryRecyclerView.adapter = allCategoriesAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categoryViewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            categories.let {
                allCategoriesAdapter.categories = it
            }
        }

        binding.apply {
            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            addCategoryButton.setOnClickListener{
//                categoryViewModel.setInsertCategoryCallback { category : Category ->
//                    categoryViewModel.insert(category)
//                }

                findNavController().navigate(R.id.action_allCategoriesFragment_to_newCategoryFragment)
            }

        }
    }

}