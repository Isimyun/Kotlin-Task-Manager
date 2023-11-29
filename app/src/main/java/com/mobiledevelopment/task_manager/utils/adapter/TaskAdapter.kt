package com.mobiledevelopment.task_manager.utils.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobiledevelopment.task_manager.databinding.TaskItemsBinding
import com.mobiledevelopment.task_manager.utils.model.TaskData

class TaskAdapter(private val list: MutableList<TaskData>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private var listener : TaskAdapterClicksInterface? = null
    fun setListener(listener : TaskAdapterClicksInterface) {
        this.listener = listener
    }

    inner class TaskViewHolder(val binding: TaskItemsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.todoTask.text = this.task

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