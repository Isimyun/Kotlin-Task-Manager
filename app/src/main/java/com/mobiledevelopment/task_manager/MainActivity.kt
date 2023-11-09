package com.mobiledevelopment.task_manager

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToTaskList(view: View) {
        val intent = Intent(this, TaskListActivity::class.java)
        startActivity(intent)
    }

    fun goToTaskDetails(view: View) {
        val intent = Intent(this, TaskDetailsActivity::class.java)
        startActivity(intent)
    }
}
