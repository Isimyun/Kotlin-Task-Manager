package com.mobiledevelopment.task_manager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.mobiledevelopment.task_manager.R
import com.mobiledevelopment.task_manager.databinding.FragmentHomeBinding

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

        binding.btnRandomizer.setOnClickListener {
            navigateToRandomizer()
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

    private fun navigateToRandomizer() {
        navController.navigate(R.id.action_homeFragment_to_randomizerFragment)
    }

    // Inside the function where you perform logout
    private fun performLogout() {
        // Use FirebaseAuth to sign out the user
        FirebaseAuth.getInstance().signOut()

        // After signing out, navigate to the sign-in fragment
        navController.navigate(R.id.action_homeFragment_to_signInFragment)

        // Clear the back stack
        navController.popBackStack(R.id.signInFragment, false)

        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()


    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
    }

}