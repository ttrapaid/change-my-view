package com.example.changemyview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class ReportUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_user)

        // variables for EditTexts
        val emailText = findViewById<EditText>(R.id.userEmail)
        val reasonText = findViewById<EditText>(R.id.Reason)


        // set up submitButton
        val submitButton = findViewById<Button>(R.id.Submit)
        submitButton.setOnClickListener {
            // gather inputs from EditTexts
            val name = emailText.text.toString().trim()
            val reason = reasonText.text.toString().trim()

            // if the user didn't fill out an EditText, ask them to fill out fields
            if(name.isEmpty() or reason.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_LONG).show()

            // else, submit report to database
            } else {

                // get database reference
                val db = FirebaseDatabase.getInstance().getReference("Reports")

                // combine the texts into one string for the database
                val id = db.push().key
                val combinedReport = "$name: $reason"

                // add to database and let user know when it is completed
                db.child(id!!).setValue(combinedReport).addOnCompleteListener {
                    Toast.makeText(this, "Report Successfully Submitted", Toast.LENGTH_LONG).show()
                }

            }
        }

    }
}