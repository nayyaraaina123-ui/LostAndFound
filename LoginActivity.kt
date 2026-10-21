package com.rpl1.lostnfound

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var ivPasswordToggle: ImageView
    private lateinit var rgAccountType: RadioGroup
    private lateinit var cbRemember: CheckBox
    private lateinit var btnLogin: Button

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Read data sent via Explicit Intent from MainActivity
        val previousUserName = intent.getStringExtra("EXTRA_USER_NAME") ?: ""
        val previousRole = intent.getStringExtra("EXTRA_ROLE") ?: ""

        // Initialize UI Components
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        ivPasswordToggle = findViewById(R.id.ivPasswordToggle)
        rgAccountType = findViewById(R.id.rgAccountType)
        cbRemember = findViewById(R.id.cbRemember)
        btnLogin = findViewById(R.id.btnLogin)

        // Pre-fill email if previous user name was passed
        if (previousUserName.isNotEmpty() && previousUserName != "Pengguna") {
            etEmail.setText("${previousUserName.lowercase().replace(" ", "")}@gmail.com")
        }

        // Password Visibility Toggle
        ivPasswordToggle.setOnClickListener {
            if (isPasswordVisible) {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                isPasswordVisible = false
            } else {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                isPasswordVisible = true
            }
            etPassword.setSelection(etPassword.text.length)
        }

        // Login Button Click Listener
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                // Toast Feedback for empty fields
                Toast.makeText(this, "Mohon isi Email dan Password terlebih dahulu!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val selectedRadioId = rgAccountType.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioId)
            val selectedRole = selectedRadioButton?.text?.toString() ?: previousRole.ifEmpty { "Siswa" }

            // User Feedback: Toast
            Toast.makeText(this, "Login Berhasil! Selamat Datang, $email", Toast.LENGTH_SHORT).show()

            // User Feedback: Snackbar with Action Button
            val rootView = findViewById<android.view.View>(R.id.mainLogin)
            val rememberText = if (cbRemember.isChecked) " (Sesi Disimpan)" else ""
            Snackbar.make(rootView, "Akun $selectedRole Terverifikasi$rememberText", Snackbar.LENGTH_LONG)
                .setAction("MASUK BERANDA") {
                    // Explicit Intent to navigate from LoginActivity to HomeActivity
                    val intentHome = Intent(this@LoginActivity, HomeActivity::class.java)
                    intentHome.putExtra("EXTRA_USER_EMAIL", email)
                    intentHome.putExtra("EXTRA_USER_NAME", previousUserName.ifEmpty { email.substringBefore("@") })
                    intentHome.putExtra("EXTRA_ROLE", selectedRole)
                    startActivity(intentHome)
                    finish()
                }
                .show()
        }
    }
}