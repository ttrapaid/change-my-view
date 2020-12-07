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

        // set up "button" for matches. Start MatchesActivity onClick
        val buttonMatch = findViewById<TextView>(R.id.matchSelect)
        buttonMatch.setOnClickListener {
            val intent = Intent(this@DashboardActivity, MatchesActivity::class.java)
            startActivity(intent)
        }

        // set up "button" for topics. Start TopicsActivity onClick
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
            // when click on report, start the report activity
            R.id.report -> {
                val intent = Intent(this@DashboardActivity, ReportUserActivity::class.java)
                startActivity(intent)
                true
            // when click on help, display AlertDialog with tips on app usage
            } R.id.help -> {
                AlertDialog.Builder(this)
                    .setMessage(getString(R.string.help_menu1) + "\n\n" + getString(R.string.help_menu2) + "\n\n" + getString(R.string.help_menu3))
                    .setCancelable(true).create().show()
                true
            }
            else -> false
        }
    }
}