package com.example.changemyview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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
            R.id.report -> {
                val intent = Intent(this@DashboardActivity, ReportUserActivity::class.java)
                startActivity(intent)
                true
            } R.id.help -> {
                AlertDialog.Builder(this)
                    .setMessage("   In this application, you may select a controversial topic to be for or against. As soon as someone else marks an opposing viewpoint on your topic, " +
                            "their email will appear in your matches. If you do not see any matches, then come visit the app the next day to check if you have been paired. \n\n   This is a " +
                            "place to have your viewpoints on life challenged in a respectful way. If you are harassed by another user, please report them by using our report feature. " +
                            "We will make sure to take appropriate action. \n\n   Have fun and keep an open mind!")
                    .setCancelable(true).create().show()
                true
            }
            else -> false
        }
    }
}