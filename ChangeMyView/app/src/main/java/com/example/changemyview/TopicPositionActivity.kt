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
import com.google.firebase.database.*

class TopicPositionActivity : AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topicposition)

        // get references to database
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        //Set the Topic title up:
        // Get title from intent's extra
        val intent = getIntent()
        val extras = intent.extras
        var title = "Default Topic Title"
        if (extras != null){
            title = extras[TopicsActivity.TITLE] as String
        }
        // set title of the topic for the textView
        val titleView = findViewById<TextView>(R.id.position_title)
        titleView.text = title

        // Set up Listener for when the user clicks on FOR
        val forView = findViewById<TextView>(R.id.position_for)
        forView.setOnClickListener(
            View.OnClickListener { v: View ->
                Toast.makeText(
                    applicationContext,
                    "You indicated you are FOR this topic",
                    Toast.LENGTH_LONG
                ).show()
                updatePosition(title, FOR)
                finish()
            }
        )

        //set up listener for AGAINST option
        val againstView = findViewById<TextView>(R.id.position_against)
        againstView.setOnClickListener(
            View.OnClickListener { v: View ->
                Toast.makeText(
                    applicationContext,
                    "You indicated you are AGAINST this topic",
                    Toast.LENGTH_LONG
                ).show()
                updatePosition(title, AGAINST)
                finish()
            }
        )
    }

    // Updates the database to reflect this users stance on the topic,
    // as well as adds this user to this topic's list of for/against users
    fun updatePosition(topic: String, position: String){
        //get the current user
        var currentUser = mAuth!!.currentUser!!
        //update this users stance
        mDatabaseReference!!.child(currentUser.uid).child("Topics").child(topic).setValue(position)
        //add this user to this topic's list of for/against users
        mDatabase!!.reference.child("Topics").child(topic).child(position).child(currentUser.uid).setValue(position)
    }

    companion object{
        val FOR = "FOR"
        val AGAINST = "AGAINST"
    }
}