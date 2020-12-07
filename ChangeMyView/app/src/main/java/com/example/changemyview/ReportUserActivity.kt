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

        val emailText = findViewById<EditText>(R.id.userEmail)
        val reasonText = findViewById<EditText>(R.id.Reason)

        val submitButton = findViewById<Button>(R.id.Submit)
        submitButton.setOnClickListener {
            var name = emailText.text.toString().trim()
            var reason = reasonText.text.toString().trim()

            if(name.isEmpty() or reason.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_LONG).show()
            } else {

                val db = FirebaseDatabase.getInstance().getReference("Reports")

                val id = db.push().key
                val combinedReport = "$name: $reason"
                db.child(id!!).setValue(combinedReport).addOnCompleteListener() {
                    Toast.makeText(this, "Report Successfully Submitted", Toast.LENGTH_LONG).show()
                }

            }
        }

    }
}