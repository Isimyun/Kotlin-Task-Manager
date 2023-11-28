package com.mobiledevelopment.task_manager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mobiledevelopment.task_manager.databinding.FragmentTaskListBinding

class TaskListFragment : Fragment(), AddTaskPopUpFragment.DialogNextBtnClickListener {
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseRef : DatabaseReference
    private lateinit var navController : NavController
    private lateinit var binding : FragmentTaskListBinding
    private lateinit var popUpFragment : AddTaskPopUpFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        registerEvents()
    }

    private fun registerEvents() {
        binding.addTaskBtn.setOnClickListener {
            popUpFragment = AddTaskPopUpFragment()
            popUpFragment.setListener(this)
            popUpFragment.show(
                childFragmentManager,
                "AddTaskPopUpFragment"
            )
        }
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference.child("Tasks").child(auth.currentUser?.uid.toString())
    }

    override fun onSaveTask(task: String, etAddTask: TextInputEditText) {
        databaseRef.push().setValue(task).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Task added successfully!", Toast.LENGTH_SHORT).show()
                etAddTask.text = null
            }
            else {
                Toast.makeText(context, "Failed to add task", Toast.LENGTH_SHORT).show()
            }

            popUpFragment.dismiss()
        }
    }
}