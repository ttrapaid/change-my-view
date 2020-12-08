package com.example.changemyview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MatchesActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference
    private var mAuth: FirebaseAuth? = null
    private var matches: ArrayList<String> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches)

        val matchListView = findViewById<ListView>(R.id.matches_listView)
        val mAdapter = ArrayAdapter(this, R.layout.match, matches)
        val topics = HashMap<String, ArrayList<String>>()

        matchListView.adapter = mAdapter

        mDatabase = FirebaseDatabase.getInstance().getReference("Users")
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth!!.currentUser!!
        val topicDict = HashMap<String, String>()

        // listener for when user clicks a specific match
        // will show new screen with that user's info, as well as
        // matched topics and the option to start a conversation
        matchListView.onItemClickListener = AdapterView.OnItemClickListener { _, view, _, _ ->
            val textView = view as TextView
            val intent = Intent(this@MatchesActivity, ViewMatchActivity::class.java)
            intent.putExtra("email", textView.text.toString())
            intent.putExtra("topics", topics[textView.text.toString()])
            startActivity(intent)
        }


        // executes the listener for mDatabase.child(currentUser.uid)
        mDatabase.child(currentUser.uid).child("DUMMY")
        mDatabase.child(currentUser.uid).child("DUMMY").removeValue()

        mDatabase.child(currentUser.uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (topic in dataSnapshot.child("Topics").children) {
                    // store the logged in user's topics and their stance
                    topicDict[topic.key.toString()] = topic.value.toString()
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext, "Error listing matches! Please try again later", Toast.LENGTH_LONG).show()
            }
        })


        // executes the listener for mDatabase
        mDatabase.child("DUMMY")
        mDatabase.child("DUMMY").removeValue()

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (user in dataSnapshot.children) {
                    for (topic in user.child("Topics").children) {
                        // conditional for matching
                        // curr-user in loop != logged in user
                        // curr-topic in loop exists in topicDict (logged-in users selected topics)
                        // curr-user curr-topic stance != logged-in user curr-topic stance
                        if (user.key.toString() != currentUser.uid && topic.key.toString() in topicDict.keys && topic.value.toString() != topicDict[topic.key.toString()]) {
                            if (topics[user.child("email").value.toString()] == null) {
                                topics[user.child("email").value.toString()] = ArrayList()
                            }

                            // stores matched topic (the logged in user and curr user in the loop)
                            topics[user.child("email").value.toString()]!!.add(topic.key.toString())

                            // launches function that will handle adding the match to listView
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
        // if the match does not already exist in the list, add them
        if (email !in matches) {
            matches.add(email)
            mAdapter.notifyDataSetChanged()
            matchListView.adapter = mAdapter
        }
    }


    companion object {
        val TOPIC_LIST = listOf("Legalize Marijuana", "Mandatory Vaccinations", "Lower the Drinking Age to 18", "Ponies4All", "Free Healthcare", "Euthanization", "Death Penalty")
    }
}