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
    private lateinit var binding: FragmentAddTaskPopUpBinding
    private lateinit var listener : DialogNextBtnClickListener
    private var taskData : TaskData? = null

    fun setListener(listener: TaskListFragment) {
        this.listener = listener
    }

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    interface DialogNextBtnClickListener {
        fun onSaveTask(task : String, etAddTask : TextInputEditText)
        fun onUpdateTask(taskData : TaskData, etAddTask : TextInputEditText)
    }


}