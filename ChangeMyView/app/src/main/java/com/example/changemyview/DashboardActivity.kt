package com.example.changemyview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val buttonMatch = findViewById<Button>(R.id.matchButton)
        buttonMatch.setOnClickListener {
            val intent = Intent(this@DashboardActivity, MatchesActivity::class.java)
            startActivity(intent)
        }

        val buttonTopic = findViewById<Button>(R.id.topicButton)
        buttonTopic.setOnClickListener {
            val intent = Intent(this@DashboardActivity, TopicsActivity::class.java)
            startActivity(intent)
        }

    }
}