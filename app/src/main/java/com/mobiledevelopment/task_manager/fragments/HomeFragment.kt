package com.mobiledevelopment.task_manager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mobiledevelopment.task_manager.R
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.mobiledevelopment.task_manager.databinding.FragmentHomeBinding
import com.mobiledevelopment.task_manager.databinding.FragmentSignInBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

        binding.btnProfile.setOnClickListener {
            navigateToProfileFragment()
        }

        binding.btnTaskList.setOnClickListener {
            navigateToTaskListFragment()
        }

        binding.btnLogout.setOnClickListener {
            performLogout()
        }
    }

    // Inside the function where you navigate to TaskListFragment
    private fun navigateToTaskListFragment() {
       navController.navigate(R.id.action_homeFragment_to_taskListFragment)
    }

    // Inside the function where you navigate to ProfileFragment
    private fun navigateToProfileFragment() {
        navController.navigate(R.id.action_homeFragment_to_profileFragment)
    }

    // Inside the function where you perform logout
    private fun performLogout() {
        navController.navigate(R.id.action_homeFragment_to_signInFragment)
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
    }

}