package com.example.changemyview

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// referenced Project 7
class RegistrationActivity : AppCompatActivity() {
    private var emailTV: EditText? = null
    private var usernameTV: EditText? = null
    private var passwordTV: EditText? = null
    private var register: Button? = null
    private var validator = Validators()
    private var confirmPasswordTV: EditText? = null


    private var mAuth: FirebaseAuth? = null
    private var mDatabaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mAuth               = FirebaseAuth.getInstance()
        mDatabaseReference  = FirebaseDatabase.getInstance().reference.child("Users")

        emailTV       = findViewById(R.id.newEmail)
        usernameTV    = findViewById(R.id.newUsername)
        passwordTV    = findViewById(R.id.newPassword)
        register      = findViewById(R.id.registerUser)

        confirmPasswordTV = findViewById(R.id.confirmPassword)


        register!!.setOnClickListener { registerNewUser() }
    }

    private fun registerNewUser() {
        val email: String = emailTV!!.text.toString()
        val password: String = passwordTV!!.text.toString()
        val confirmPassword: String = confirmPasswordTV!!.text.toString()

        if (!validator.validEmail(email)) {
            Toast.makeText(applicationContext, "Please enter a valid email...", Toast.LENGTH_LONG).show()
            return
        }
        if (!validator.validPassword(password, confirmPassword)) {
            Toast.makeText(applicationContext, "Please enter a valid password!", Toast.LENGTH_LONG).show()
            return
        }

        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Registration successful!", Toast.LENGTH_LONG).show()

                    val currentUser = mAuth!!.currentUser!!
                    mDatabaseReference!!.child(currentUser.uid)
                    val user = User(currentUser.uid, currentUser.email)

//                    for (topic in TOPIC_LIST)
//                        mDatabaseReference!!.child(currentUser.uid).child("Topics").child(topic).setValue("UNDECIDED")

                    mDatabaseReference!!.child(currentUser.uid).child("email").setValue(currentUser.email.toString())

                    val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Registration failed! Please try again later", Toast.LENGTH_LONG).show()
                }
            }
    }



}