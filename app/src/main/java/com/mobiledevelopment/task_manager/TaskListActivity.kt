package com.mobiledevelopment.task_manager

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TaskListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        val taskListNameBundle: Bundle? = intent.extras
        val taskListName = taskListNameBundle?.getString("task_name")

        val taskListDescBundle: Bundle? = intent.extras
        val taskListDescription = taskListDescBundle?.getString("task_description")

        val txvTaskListName: TextView = findViewById(R.id.txvTaskListName)
        txvTaskListName.text = taskListName

        txvTaskListName.setOnClickListener {
            val intent = Intent(this, TaskDetailsActivity::class.java)
            intent.putExtra("task_description_from_task_list", taskListDescription)
            intent.putExtra("task_name_from_task_list", taskListName)
            startActivity(intent)
        }
    }
}
