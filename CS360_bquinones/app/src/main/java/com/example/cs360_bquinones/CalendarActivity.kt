package com.example.cs360_bquinones


import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.Toast
import androidx.activity.ComponentActivity

class CalendarActivity : ComponentActivity() {

    private lateinit var dbHelper: EventDatabaseHelper
    private lateinit var eventTitleInput: EditText
    private lateinit var eventDateInput: EditText
    private lateinit var eventTimeInput: EditText
    private lateinit var addEventButton: Button
    private lateinit var eventDisplay: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calendar)

        dbHelper = EventDatabaseHelper(this)
        eventTitleInput = findViewById(R.id.eventTitleInput)
        eventDateInput = findViewById(R.id.editTextDate)
        eventTimeInput = findViewById(R.id.editTextTime)
        addEventButton = findViewById(R.id.addEventButton)
        eventDisplay = findViewById(R.id.eventDisplay0)

        // Add Event Button Listener
        addEventButton.setOnClickListener {
            val title = eventTitleInput.text.toString()
            val date = eventDateInput.text.toString()
            val time = eventTimeInput.text.toString()

            if (title.isNotEmpty() && date.isNotEmpty()) {
                if (dbHelper.addEvent(title, date, time)) {
                    Toast.makeText(this, "Event added!", Toast.LENGTH_SHORT).show()
                    refreshEventDisplay()
                } else {
                    Toast.makeText(this, "Failed to add event!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter both title and date.", Toast.LENGTH_SHORT).show()
            }
        }

        // Initial display of events
        refreshEventDisplay()

        // Intent to change pages
        val summaryButton = findViewById<Button>(R.id.goToSummary)
        val smsButton = findViewById<Button>(R.id.goToSMSNotifications)

        summaryButton.setOnClickListener {
            startActivity(Intent(this, SummaryActivity::class.java))
        }

        smsButton.setOnClickListener {
            startActivity(Intent(this, SmsNotificationActivity::class.java))
        }

    }

    private fun refreshEventDisplay() {
        val events = dbHelper.getAllEvents()
        val eventStrings = events.map { "${it.title}\n${it.date}\n${it.time}" } // Format for GridView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, eventStrings)
        eventDisplay.adapter = adapter
    }
}