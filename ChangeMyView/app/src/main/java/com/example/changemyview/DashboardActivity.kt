package com.example.changemyview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val buttonMatch = findViewById<TextView>(R.id.matchSelect)
        buttonMatch.setOnClickListener {
            val intent = Intent(this@DashboardActivity, MatchesActivity::class.java)
            startActivity(intent)
        }

        val buttonTopic = findViewById<TextView>(R.id.topicSelect)
        buttonTopic.setOnClickListener {
            val intent = Intent(this@DashboardActivity, TopicsActivity::class.java)
            startActivity(intent)
        }

    }

    // Create Options Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }

    // Process clicks on Options Menu items
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.info -> {
                val intent = Intent(this@DashboardActivity, ReportUserActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
    }
}