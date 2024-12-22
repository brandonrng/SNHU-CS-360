package com.example.cs360_bquinones

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity

class SmsNotificationActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sms_notifications)

        // Initialize UI elements
        val phoneNumberEditText = findViewById<EditText>(R.id.editTextPhonetic)
        val submitButton = findViewById<Button>(R.id.submitButton)
        val noThanksButton = findViewById<Button>(R.id.noThanksButton)

        // Handle Submit button click
        submitButton.setOnClickListener {
            val phoneNumber = phoneNumberEditText.text.toString()

            if (isValidPhoneNumber(phoneNumber)) {
                Toast.makeText(this, "Phone number submitted: $phoneNumber", Toast.LENGTH_SHORT).show()
                // You can add logic here to save the phone number to the database or send it to your server
            } else {
                Toast.makeText(this, "Invalid phone number. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle No Thanks button click
        noThanksButton.setOnClickListener {
            Toast.makeText(this, "You have opted out of SMS notifications.", Toast.LENGTH_SHORT).show()
            // Navigate back or to another activity if needed
            finish()
        }

        // Intent to change pages
        val summaryButton = findViewById<Button>(R.id.goToSummary)
        val calendarButton = findViewById<Button>(R.id.goToCalendar)

        summaryButton.setOnClickListener {
            startActivity(Intent(this, SummaryActivity::class.java))
        }

        calendarButton.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }

    }

    // Function to validate phone number
    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // Simple validation: Ensure phone number is not empty and matches a basic pattern
        val phoneRegex = Regex("^\\+?[0-9]{10,15}$") // Accepts numbers with optional "+" and 10-15 digits
        return phoneNumber.isNotEmpty() && phoneRegex.matches(phoneNumber)
    }
}