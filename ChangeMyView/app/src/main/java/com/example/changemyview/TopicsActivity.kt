package com.example.changemyview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentActivity
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TopicsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "In TopicsActivity onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)

        // set up ListView
        val listView = findViewById<ListView>(R.id.topics_listview)

        listView.adapter = ArrayAdapter(
            this, R.layout.topic_item,
            resources.getStringArray(R.array.topics_list)
        )

        // Enable filtering when the user types in the virtual keyboard
        listView.isTextFilterEnabled = true

        // Set a setOnItemClickListener on the ListView
        listView.onItemClickListener = OnItemClickListener { _, view, _, _ ->
            val textView = view as TextView
            // Open activity for details and choose a position
            val intent = Intent(
                this@TopicsActivity, TopicPositionActivity::class.java)

            intent.putExtra(TITLE, view.text.toString())
            startActivity(intent)
        }

        //set up navigation buttons
        val menuButton = findViewById<Button>(R.id.topic_to_menu)
        menuButton.setOnClickListener {
            val intent = Intent(this@TopicsActivity, DashboardActivity::class.java)
            startActivity(intent)
        }

        val matchesButton = findViewById<Button>(R.id.topic_to_matches)
        matchesButton.setOnClickListener {
            val intent = Intent(this@TopicsActivity, MatchesActivity::class.java)
            startActivity(intent)
        }

    }

    companion object {
        val TAG = "ChangeMyView - Topics"
        val TITLE = "title"
    }

}