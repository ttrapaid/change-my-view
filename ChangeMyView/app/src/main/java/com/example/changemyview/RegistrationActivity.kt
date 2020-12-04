package com.example.changemyview

import android.content.Intent
import android.os.Bundle
import android.service.autofill.Validators
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {
    private var emailTV: EditText? = null
    private var usernameTV: EditText? = null
    private var passwordTV: EditText? = null
    private var register: Button? = null
    private var validator = Validators()
    private var confirmPasswordTV: EditText? = null


    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mAuth       = FirebaseAuth.getInstance()


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
                    val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Registration failed! Please try again later", Toast.LENGTH_LONG).show()
                }
            }
    }

}