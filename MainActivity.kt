package com.rpl1.lostnfound

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etUserName: EditText
    private lateinit var rgRoleFirst: RadioGroup
    private lateinit var btnLoginFirst: Button
    private lateinit var tvAnonymous: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI components
        etUserName = findViewById(R.id.etUserName)
        rgRoleFirst = findViewById(R.id.rgRoleFirst)
        btnLoginFirst = findViewById(R.id.btnLoginFirst)
        tvAnonymous = findViewById(R.id.tvAnonymous)

        // Button Login Click Listener
        btnLoginFirst.setOnClickListener {
            val userName = etUserName.text.toString().trim()
            val selectedRadioId = rgRoleFirst.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioId)
            val selectedRole = selectedRadioButton?.text?.toString() ?: "Siswa / Siswi"

            // Explicit Intent to navigate from MainActivity to LoginActivity
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            intent.putExtra("EXTRA_USER_NAME", userName.ifEmpty { "Pengguna" })
            intent.putExtra("EXTRA_ROLE", selectedRole)
            startActivity(intent)
        }

        // Anonymous Link Click Listener
        tvAnonymous.setOnClickListener {
            Toast.makeText(this, "Masuk sebagai Pengguna Anonim", Toast.LENGTH_SHORT).show()

            // Explicit Intent to navigate from MainActivity directly to HomeActivity
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            intent.putExtra("EXTRA_USER_NAME", "Anonim")
            intent.putExtra("EXTRA_ROLE", "Umum")
            startActivity(intent)
        }
    }
}