package com.example.todoapp.ui.home.category

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentNewCategoryBinding
import com.example.todoapp.model.Category
import com.example.todoapp.viewModel.CategoryViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class NewCategoryFragment : Fragment() {
    private lateinit var binding: FragmentNewCategoryBinding
    private val categoryViewModel: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val galleryActivityLauncher = registerForActivityResult<Array<String>, Uri>(
            ActivityResultContracts.OpenDocument()
        ) { result ->
            if (result != null) {
                binding.imageTextView.text = result.toString()
            } else {
                Log.d("TAG", "onActivityResult: the result is null for some reason")
            }
        }

        binding.apply {
            backButton.setOnClickListener {
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
            imageLayout.setOnClickListener {
                galleryActivityLauncher.launch(arrayOf("image/*"))
            }
            createButton.setOnClickListener {
                if(nameEditText.text.toString() == ""){
                    nameEditText.error = getString(R.string.category_name_error)
                    return@setOnClickListener
                }
                else{
                    nameEditText.error = null
                }
                categoryViewModel.insert(
                    Category(
                        0,
                        nameEditText.text.toString(),
                        imageTextView.text.toString(),
                        0
                    )
                )

//                categoryViewModel.insertCategoryCallback.observe(viewLifecycleOwner) { callback ->
//                    Log.d("NewCategoryFragment", "New Category: ${nameEditText.text.toString()}")
//                    callback.invoke(
//                        Category(
//                            0,
//                            nameEditText.text.toString(),
//                            imageTextView.text.toString(),
//                            0
//                        )
//                    )
//
//                }
                parentFragmentManager.popBackStack()
            }
        }
    }

}