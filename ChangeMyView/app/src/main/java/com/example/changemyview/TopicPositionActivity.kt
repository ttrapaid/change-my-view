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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TopicPositionActivity : AppCompatActivity() {

    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topicposition)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        //Set the Topic title up
        val intent = getIntent()
        val extras = intent.extras
        var title = "Default Topic Title"
        //var userID: String
        if (extras != null){
            title = extras[TopicsActivity.TITLE] as String
            //userID = extras[MainActivity.USER_ID] as String
        }
        
        val titleView = findViewById<TextView>(R.id.position_title)
        titleView.text = title

        // Record which stance this user has on this topic
        val forView = findViewById<TextView>(R.id.position_for)
        forView.setOnClickListener(
            View.OnClickListener { v: View ->
                Toast.makeText(
                    applicationContext,
                    "You indicated you are FOR this topic",
                    Toast.LENGTH_LONG
                ).show()
                //TODO - update database to reflect that this user is for this topic
                //database.reference.child("Users").child(currentUser.UID).child("Topics").child(topicName).setValue(stance)
                finish()
            }
        )

        val againstView = findViewById<TextView>(R.id.position_against)
        againstView.setOnClickListener(
            View.OnClickListener { v: View ->
                Toast.makeText(
                    applicationContext,
                    "You indicated you are AGAINST this topic",
                    Toast.LENGTH_LONG
                ).show()
                //TODO - update database to reflect this user is against this topic
                finish()
            }
        )
    }
}