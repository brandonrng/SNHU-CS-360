package com.example.cs360_bquinones

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.activity.ComponentActivity

class SummaryActivity : ComponentActivity(){

    private lateinit var dbHelper: EventDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.summary)

        // Initialize database helper
        dbHelper = EventDatabaseHelper(this)

        // Initialize UI elements
        val eventGridView = findViewById<GridView>(R.id.eventDisplay1)
        val calendarButton = findViewById<Button>(R.id.goToCalendar)
        val smsNotificationButton = findViewById<Button>(R.id.goToSMSNotifications)

        // Load events into the GridView
        loadEventsIntoGridView(eventGridView)

        // Set up navigation to Summary screen
        calendarButton.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)        }

        // Set up navigation to SMS Notifications screen
        smsNotificationButton.setOnClickListener {
            val intent = Intent(this, SmsNotificationActivity::class.java)
            startActivity(intent)
        }
    }
    // Function to load events into GridView
    private fun loadEventsIntoGridView(gridView: GridView) {
        // Retrieve events from the database
        val events = dbHelper.getAllEvents()

        // Convert events into a format suitable for display
        val eventDetails = events.map { event ->
            "${event.title}\n${event.date} ${event.time}"
        }

        // Use a simple adapter for displaying events
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, eventDetails)
        gridView.adapter = adapter

        // Set item click listener for grid items
        gridView.setOnItemClickListener { _, _, position, _ ->
            val selectedEvent = eventDetails[position]
            Toast.makeText(this, "Selected: $selectedEvent", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}