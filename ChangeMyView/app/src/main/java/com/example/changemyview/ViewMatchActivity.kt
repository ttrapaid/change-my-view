package com.example.changemyview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ViewMatchActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmatch)


        val email = intent.extras?.get("email")
        val matchTV = findViewById<TextView>(R.id.match_text)
        matchTV.text = "You matched with ${email.toString()}!"

        val button = findViewById<Button>(R.id.start)

        button.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND)

            emailIntent.setType("plain/text")
            emailIntent.putExtra(Intent.EXTRA_EMAIL, email.toString())
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "")

            startActivity(emailIntent)
        }

    }


}