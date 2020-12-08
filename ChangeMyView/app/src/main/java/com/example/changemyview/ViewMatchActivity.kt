package com.example.changemyview

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ViewMatchActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmatch)

        // retrieve data from intent
        val email = intent.extras?.get("email")
        val topic = intent.extras?.get("topics")

        val matchTV = findViewById<TextView>(R.id.match_text)
        val topicTV = findViewById<TextView>(R.id.topic_list)

        // post intent data to the screen/textviews
        matchTV.text = "You matched with ${email.toString()}!"
        topicTV.text = "Your controversial topics include: ${topic.toString()}"

        // create button and listener to start email app
        val button = findViewById<Button>(R.id.start)
        button.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)

            emailIntent.type = "plain/text"

            // put the email in the "to:" field
            emailIntent.putExtra(Intent.EXTRA_EMAIL, email.toString())

            // set the subject and body to be empty - user will change
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "")

            // launch email intent
            startActivity(emailIntent)
        }

        //set up navigation buttons
        val menuButton = findViewById<Button>(R.id.match_to_menu)
        menuButton.setOnClickListener {
            val intent = Intent(this@ViewMatchActivity, DashboardActivity::class.java)
            startActivity(intent)
        }



    }


}