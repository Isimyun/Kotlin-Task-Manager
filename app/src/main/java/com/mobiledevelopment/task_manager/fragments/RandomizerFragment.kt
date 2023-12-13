package com.mobiledevelopment.task_manager.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobiledevelopment.task_manager.databinding.FragmentRandomizerBinding
import java.util.*
import kotlin.math.sqrt
import com.mobiledevelopment.task_manager.R

class RandomizerFragment : Fragment(), SensorEventListener {

    private lateinit var binding: FragmentRandomizerBinding
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private val shakeThreshold = 30
    private var lastShakeTime: Long = 0
    private lateinit var taskRef: DatabaseReference
    private lateinit var taskList: MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRandomizerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    // Initialize the sensor manager, then use it to get the accelerometer from the device
    // Get tasks from database
    private fun init(view: View) {
        sensorManager = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        taskRef = FirebaseDatabase.getInstance().getReference("Tasks")
        taskList = mutableListOf()

        // Display error message in case there isn't an accelerometer
        if (accelerometer == null) {
            Toast.makeText(context, "Accelerometer is not detected", Toast.LENGTH_SHORT).show()
        }

        fetchTasksFromDatabase()
    }

    private fun fetchTasksFromDatabase() {
        // Make sure the data is from the user that is logged in
        val currentUser = FirebaseAuth.getInstance().currentUser

        // If user exist, save user ID
        if (currentUser != null) {
            val userId = currentUser.uid

            // Takes the tasks ONLY FROM CURRENT USER, avoids taking data from another user
            taskRef.child(userId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    taskList.clear()
                    for (taskSnapshot in snapshot.children) {
                        val taskName = taskSnapshot.getValue(String::class.java)
                        taskName?.let {
                            taskList.add(it)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Database Error", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            // Handle the case where the user is not authenticated
            Toast.makeText(context, "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }

    // When phone is shaken
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    // When phone is idle
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for this example
    }

    @SuppressLint("SetTextI18n")
    //
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            // Value of x, y, z will keep changing to show that accelerometer is working
            binding.accelerometerValues.text =
                "X: ${event.values[0]}, Y: ${event.values[1]}, Z: ${event.values[2]}"

            val currentTime = System.currentTimeMillis()
            if (currentTime - lastShakeTime > 1000) {
                // Update the last shake time to prevent rapid shakes
                lastShakeTime = currentTime

                // Check for shake detection
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]
                val acceleration =
                    sqrt(x * x + y * y + (z * z).toDouble()) - SensorManager.GRAVITY_EARTH
                if (acceleration > shakeThreshold) {
                    // Shake detected, randomly select a task and display it
                    val randomTask = getRandomTask()
                    displayTaskDialog(randomTask)
                }
            }
        }
    }

    private fun getRandomTask(): String {
        // Randomly select a task from the list
        return if (taskList.isNotEmpty()) {
            val randomIndex = Random().nextInt(taskList.size)
            taskList[randomIndex]
        } else {
            "No tasks available"
        }
    }

    private fun displayTaskDialog(task: String) {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog_layout, null)
        val dialogTitle = dialogView.findViewById<TextView>(R.id.dialogTitle)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.dialogMessage)
        val dialogButton = dialogView.findViewById<Button>(R.id.dialogButton)

        // Set the dialog title and message
        dialogTitle.text = "Task Selected"
        dialogMessage.text = "$task has been selected!"

        // Create the AlertDialog
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setView(dialogView)

        // Create and show the dialog
        val alertDialog = alertDialogBuilder.create()

        // Set button click listener
        dialogButton.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

}
