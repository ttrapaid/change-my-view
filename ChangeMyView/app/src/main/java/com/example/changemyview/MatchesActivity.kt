package com.example.changemyview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MatchesActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference
    private var mAuth: FirebaseAuth? = null
    var matches: ArrayList<String> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches)

        val matchListView = findViewById<ListView>(R.id.matches_listview)
        val mAdapter = ArrayAdapter(this, R.layout.match, matches)

        var topics = HashMap<String, ArrayList<String>>()

        matchListView.adapter = mAdapter

        mDatabase = FirebaseDatabase.getInstance().getReference("Users")
        mAuth = FirebaseAuth.getInstance()
        var currentUser = mAuth!!.currentUser!!
        var topicDict = HashMap<String, String>()

        matchListView.onItemClickListener = AdapterView.OnItemClickListener { _, view, _, _ ->
            val textView = view as TextView
            val intent = Intent(this@MatchesActivity, ViewMatchActivity::class.java)
            intent.putExtra("email", textView.text.toString())
            intent.putExtra("topics", topics[textView.text.toString()])
            startActivity(intent)
        }


        // literally just to execute the listener ...
        mDatabase.child(currentUser.uid).child("DUMMY")
        mDatabase.child(currentUser.uid).child("DUMMY").removeValue()

        mDatabase.child(currentUser.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (topic in dataSnapshot.child("Topics").children) {
                    topicDict[topic.key.toString()] = topic.value.toString()
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "Error listing matches! Please try again later", Toast.LENGTH_LONG).show()
            }
        })


        // literally just to execute the listener ...
        mDatabase.child("DUMMY")
        mDatabase.child("DUMMY").removeValue()

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (user in dataSnapshot.children) {
                    for (topic in user.child("Topics").children) {
                        if (user.key.toString() != currentUser.uid && topic.key.toString() in topicDict.keys && topic.value.toString() != topicDict[topic.key.toString()]) {
                            if (topics[user.child("email").value.toString()] == null) {
                                topics[user.child("email").value.toString()] = ArrayList<String>()
                            }

                            topics[user.child("email").value.toString()]!!.add(topic.key.toString())

                            addMatch(
                                user.child("email").value.toString(),
                                matchListView,
                                mAdapter
                            )
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, "Error listing matches! Please try again later", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun addMatch(email: String, matchListView: ListView, mAdapter: ArrayAdapter<String>) {
        if (email !in matches) {
            matches.add(email)
            mAdapter.notifyDataSetChanged()
            matchListView.adapter = mAdapter
        }
    }


    companion object {
        val TOPIC_LIST = listOf("Legalize Marijuana", "Mandatory Vaccinations", "Lower the Drinking Age to 18", "Ponies4All", "Free Healthcare", "Euthanization", "Death Penalty")
        val TAG = "ChangeMyView"
        val TITLE = "title"
    }
}