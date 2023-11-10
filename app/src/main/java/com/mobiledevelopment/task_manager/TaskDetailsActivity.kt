package com.mobiledevelopment.task_manager

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TaskDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        val bundle: Bundle? = intent.extras

        val taskName = bundle?.getString("task_name")
        val taskDescription = bundle?.getString("task_description")

        val txvTaskName: TextView = findViewById(R.id.txvTaskName)
        val txvTaskDescription: TextView = findViewById(R.id.txvTaskDescription)

        txvTaskName.text = taskName
        txvTaskDescription.text = taskDescription
    }
}
