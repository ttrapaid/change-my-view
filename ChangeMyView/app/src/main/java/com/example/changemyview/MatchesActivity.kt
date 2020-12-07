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
    var matches: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.matches)

        val matchListView = findViewById<ListView>(R.id.matches_listview)
        val mAdapter = ArrayAdapter(this, R.layout.match, matches)

        matchListView.adapter = mAdapter

        mDatabase = FirebaseDatabase.getInstance().getReference("Users")
        var currentUser = mAuth!!.currentUser!!
        var topicDict = HashMap<String, String>()

        matchListView.onItemClickListener = AdapterView.OnItemClickListener { _, view, _, _ ->
            val textView = view as TextView
            val intent = Intent(this@MatchesActivity, ViewMatch::class.java)
//            intent.putExtra("username", )
        }

        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (user in dataSnapshot.children) {
                    for (topic in user.child("Topics").children) {
                        if (user.child("UID").toString() == currentUser.uid) {
                            topicDict[topic.toString()] = topic.value.toString()
                        } else if (topic.value.toString() == topicDict[topic.toString()]) {
                            addMatch(user.child("UID").toString(), matchListView, mAdapter)
                        }
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, "Error listing matches! Please try again later", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun addMatch( userID: String, matchListView: ListView, mAdapter: ArrayAdapter<String>) {
        matches.add(userID)
        mAdapter.notifyDataSetChanged()
        matchListView.adapter = mAdapter
    }


    companion object {
        val TOPIC_LIST = listOf("Legalize Marijuana", "Mandatory Vaccinations", "Lower the Drinking Age to 18", "Ponies4All", "Free Healthcare", "Euthanization", "Death Penalty")
    }
}