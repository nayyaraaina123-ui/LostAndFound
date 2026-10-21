package com.rpl1.lostnfound

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_LOKASI = "extra_lokasi"
        const val EXTRA_STATUS = "extra_status"
        const val EXTRA_WAKTU = "extra_waktu"
        const val EXTRA_GAMBAR = "extra_gambar"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val nama = intent.getStringExtra(EXTRA_NAMA) ?: "-"
        val lokasi = intent.getStringExtra(EXTRA_LOKASI) ?: "-"
        val status = intent.getStringExtra(EXTRA_STATUS) ?: "-"
        val waktu = intent.getStringExtra(EXTRA_WAKTU) ?: "-"
        val gambar = intent.getIntExtra(EXTRA_GAMBAR, 0)

        findViewById<ImageView>(R.id.imgDetail).setImageResource(gambar)
        findViewById<TextView>(R.id.tvDetailNama).text = nama
        findViewById<TextView>(R.id.tvDetailInfo).text =
            "Status: $status\nLokasi: $lokasi\nWaktu: $waktu"

        findViewById<Button>(R.id.btnKembali).setOnClickListener {
            Toast.makeText(this, "Kembali ke beranda", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}