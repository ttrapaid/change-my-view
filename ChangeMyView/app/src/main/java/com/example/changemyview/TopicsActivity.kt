package com.example.changemyview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TopicsActivity : AppCompatActivity() {

    private lateinit var mArrayAdapter: ArrayAdapter<String>
    private lateinit var listView: ListView

    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "In TopicsActivity onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topics)

        //get instances and references for the database
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        // set up ListView
        listView = findViewById(R.id.topics_listView)

        //get reference to adapter and attach it
        mArrayAdapter = ArrayAdapter(
            this, R.layout.topic_item,
            MatchesActivity.TOPIC_LIST //resources.getStringArray(R.array.topics_list)
        )
        listView.adapter = mArrayAdapter


        // Enable filtering when the user types in the virtual keyboard
        listView.isTextFilterEnabled = true

        // Set a setOnItemClickListener on the ListView
        listView.onItemClickListener = OnItemClickListener { _, view, _, _ ->
            val textView = view as TextView
            // Open activity to choose a position
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
        const val TAG = "ChangeMyView - Topics"
        const val TITLE = "title"
    }

}