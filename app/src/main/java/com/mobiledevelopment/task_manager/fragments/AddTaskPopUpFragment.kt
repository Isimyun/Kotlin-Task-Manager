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
import com.mobiledevelopment.task_manager.utils.model.TaskData

class AddTaskPopUpFragment : DialogFragment() {
    // I used lateinit because I want program to know I will initialize before use(So I don't have to initialize now)
    private lateinit var binding: FragmentAddTaskPopUpBinding
    private lateinit var listener : DialogNextBtnClickListener
    private var taskData : TaskData? = null

    fun setListener(listener: TaskListFragment) {
        this.listener = listener
    }

    // This is the companion object
    companion object {
        const val TAG  = "AddTaskPopUpFragment"

        @JvmStatic
        fun newInstance(taskId : String, task : String) = AddTaskPopUpFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }

    // Instantiates the view
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    // When view is created, gets task data, then register events
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            taskData = TaskData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString()
            )

            binding.etAddTask.setText(taskData?.task)
        }
        registerEvents()
    }

    // When user clicks save task button, program will check whether the text field is empty
    // If field empty, prompt user to fill the blank
    // If field not empty, and task data is empty, will save as new task, but is task data not empty, will update task
    private fun registerEvents() {
        binding.btnSaveTask.setOnClickListener {
            val addTask = binding.etAddTask.text.toString()
            if (addTask.isNotEmpty()) {
                if (taskData == null) {
                    listener.onSaveTask(addTask, binding.etAddTask)
                }
                else {
                    taskData?.task = addTask
                    listener.onUpdateTask(taskData!!, binding.etAddTask)
                }
            }
            else {
                Toast.makeText(context, "Please add a task", Toast.LENGTH_SHORT).show()
            }
        }

        binding.close.setOnClickListener {
            dismiss()
        }
    }

    // Interface of the button when clicked
    interface DialogNextBtnClickListener {
        fun onSaveTask(task : String, etAddTask : TextInputEditText)
        fun onUpdateTask(taskData : TaskData, etAddTask : TextInputEditText)
    }


}