package com.mobiledevelopment.task_manager.utils.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobiledevelopment.task_manager.databinding.TaskItemsBinding
import com.mobiledevelopment.task_manager.utils.model.TaskData

class TaskAdapter(private val list: MutableList<TaskData>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // Click listener interface
    private var listener : TaskAdapterClicksInterface? = null

    // Set the click listener for the adapter
    fun setListener(listener : TaskAdapterClicksInterface) {
        this.listener = listener
    }

    // ViewHolder class for the adapter
    inner class TaskViewHolder(val binding: TaskItemsBinding) : RecyclerView.ViewHolder(binding.root)

    // Create and return a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    // Return number of tasks
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.todoTask.text = this.task

                // Set click listeners for delete and edit buttons
                binding.deleteTask.setOnClickListener {
                    listener?.onDeleteTaskBtnClicked(this)
                }

                binding.editTask.setOnClickListener {
                    listener?.onEditTaskBtnClicked(this)
                }
            }
        }
    }

    interface TaskAdapterClicksInterface {
        fun onDeleteTaskBtnClicked(taskData : TaskData)
        fun onEditTaskBtnClicked(taskData : TaskData)
    }


}