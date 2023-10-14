package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.todoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.newTaskFragment
                || destination.id == R.id.editTaskFragment
                || destination.id == R.id.viewTaskFragment
                || destination.id == R.id.editCategoryFragment
                || destination.id == R.id.newCategoryFragment
                || destination.id == R.id.allCategoriesFragment
                || destination.id == R.id.allTasksFragment
                || destination.id == R.id.categoryWithListTasksFragment) {

               binding.bottomNavigationBarView.visibility = View.GONE
            } else {
                binding.bottomNavigationBarView.visibility = View.VISIBLE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val navView = binding.bottomNavigationBarView
        navView.setupWithNavController(navController)
        navView.itemIconTintList = null
    }
}