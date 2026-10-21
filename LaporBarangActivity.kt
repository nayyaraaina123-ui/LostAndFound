package com.rpl1.lostnfound

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LaporBarangActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lapor_barang)

        val mainView = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupViews(mainView)
    }

    private fun setupViews(rootView: View) {
        val btnBack = findViewById<ImageView>(R.id.btnBackLapor)
        val btnAmbilFoto = findViewById<Button>(R.id.btnAmbilFoto)
        val rgTipeBarang = findViewById<RadioGroup>(R.id.rgTipeBarang)
        val etNamaBarang = findViewById<EditText>(R.id.etNamaBarang)
        val etLokasi = findViewById<EditText>(R.id.etLokasi)
        val etDeskripsi = findViewById<EditText>(R.id.etDeskripsi)
        val btnKirimLaporan = findViewById<Button>(R.id.btnKirimLaporan)

        btnBack.setOnClickListener {
            finish()
        }

        // Ambil Foto Button Actions
        btnAmbilFoto.setOnClickListener {
            Toast.makeText(this, "Foto berhasil diambil!", Toast.LENGTH_SHORT).show()
            Snackbar.make(rootView, "Foto ditambahkan ke laporan", Snackbar.LENGTH_SHORT)
                .setAction("BATAL") {
                    Toast.makeText(this, "Pengambilan foto dibatalkan", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        // Kirim Laporan Button Actions
        btnKirimLaporan.setOnClickListener {
            val nama = etNamaBarang.text.toString().trim()
            val lokasi = etLokasi.text.toString().trim()
            val deskripsi = etDeskripsi.text.toString().trim()

            if (nama.isEmpty() || lokasi.isEmpty() || deskripsi.isEmpty()) {
                // Toast feedback for validation
                Toast.makeText(this, "Harap isi nama barang, lokasi, dan deskripsi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tipe = if (rgTipeBarang.checkedRadioButtonId == R.id.rbHilang) "Hilang" else "Temuan"
            val currentTime = SimpleDateFormat("HH:mm 'WIB', dd/MM/yyyy", Locale.getDefault()).format(Date())

            val newReport = ReportItem(
                id = System.currentTimeMillis().toString(),
                namaBarang = nama,
                lokasi = lokasi,
                waktu = currentTime,
                tipe = tipe,
                status = "Aktif",
                deskripsi = deskripsi,
                catatan = "",
                imageType = "wallet"
            )

            // Toast feedback
            Toast.makeText(this, "Laporan '$nama' berhasil dikirim!", Toast.LENGTH_SHORT).show()

            // Snackbar feedback with Action button using Explicit Intent
            Snackbar.make(rootView, "Laporan $tipe '$nama' berhasil dibuat!", Snackbar.LENGTH_LONG)
                .setAction("LIHAT DETAIL") {
                    val intent = Intent(this, DetailBarangActivity::class.java).apply {
                        putExtra("EXTRA_REPORT_ITEM", newReport)
                    }
                    startActivity(intent)
                    finish()
                }
                .show()
        }
    }
}
