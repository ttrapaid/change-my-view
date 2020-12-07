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
                updatePosition(title, FOR)
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
                updatePosition(title, AGAINST)
                finish()
            }
        )
    }

    fun updatePosition(topic: String, position: String){
        var currentUser = mAuth!!.currentUser!!
        mDatabaseReference!!.child(currentUser.uid).child("Topics").child(topic).setValue(position)
        /*
        var userId = firebase.auth().currentUser.uid;
        return firebase.database().ref('/users/' + userId).once('value').then((snapshot) => {
        var username = (snapshot.val() && snapshot.val().username) || 'Anonymous';
        //...
        });

        var mListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var mlist = dataSnapshot.child(topic).child(position).getValue() //as Array<String>

                if (mlist != null){
                    (mlist as ArrayList<String>).add(currentUser.uid)
                } else {
                    mlist = arrayListOf<String>(currentUser.uid)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                println("loadPost:onCancelled ${databaseError.toException()}")
            }
        }
        mDatabase!!.reference.child("Topics").addListenerForSingleValueEvent(mListener)
        //mDatabaseReference!!.child("Topics").child(topic).child(position).setValue
        */
        mDatabase!!.reference.child("Topics").child(topic).child(position).child(currentUser.uid).setValue(position)
        mDatabaseReference!!.child(currentUser.uid).child("Topics").child(topic).setValue(position)
    }

    companion object{
        val FOR = "FOR"
        val AGAINST = "AGAINST"
    }
}