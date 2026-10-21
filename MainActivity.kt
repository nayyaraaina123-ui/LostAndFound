package com.rpl1.lostnfound

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Explicit Intent to open LaporanSayaActivity
        val intent = Intent(this, LaporanSayaActivity::class.java)
        startActivity(intent)
        finish()
    }
}
