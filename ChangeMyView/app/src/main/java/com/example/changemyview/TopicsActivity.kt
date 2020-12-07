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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        // set up ListView
        listView = findViewById<ListView>(R.id.topics_listview)

        mArrayAdapter = ArrayAdapter(
            this, R.layout.topic_item,
            MatchesActivity.TOPIC_LIST //resources.getStringArray(R.array.topics_list)
        )
        listView.adapter = mArrayAdapter

        setTopicColors()

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

    // TODO - set the background color of each topic according to the user's current position
    fun setTopicColors(){
        /*
        var currentUser = mAuth!!.currentUser!!
        var length = mArrayAdapter.count
        for (i in 0 until length) {
            val topicView = mArrayAdapter.getView(i, null, listView) as TextView
            val topic = topicView.text.toString()
            //mDatabaseReference!!.child(currentUser.uid).child("Topics").child(topic).setValue(position
        }
        */
    }

    companion object {
        val TAG = "ChangeMyView - Topics"
        val TITLE = "title"
    }

}