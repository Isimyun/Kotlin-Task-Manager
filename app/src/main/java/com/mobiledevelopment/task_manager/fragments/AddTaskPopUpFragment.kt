package com.mobiledevelopment.task_manager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.mobiledevelopment.task_manager.databinding.FragmentAddTaskPopUpBinding

class AddTaskPopUpFragment : DialogFragment() {
    private lateinit var binding: FragmentAddTaskPopUpBinding
    private lateinit var listener : DialogNextBtnClickListener

    fun setListener(listener: TaskListFragment) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerEvents()
    }

    private fun registerEvents() {
        binding.btnSaveTask.setOnClickListener {
            val addTask = binding.etAddTask.text.toString()
            if (addTask.isNotEmpty()) {
                listener.onSaveTask(addTask, binding.etAddTask)
            }
            else {
                Toast.makeText(context, "Please add a task", Toast.LENGTH_SHORT).show()
            }
        }

        binding.close.setOnClickListener {
            dismiss()
        }
    }

    interface DialogNextBtnClickListener {
        fun onSaveTask(task : String, etAddTask : TextInputEditText)
    }


}