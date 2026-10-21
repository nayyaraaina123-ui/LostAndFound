package com.rpl1.lostnfound

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LaporanSayaActivity : AppCompatActivity() {

    // Sample Report Items matching the design
    private val pencilReport = ReportItem(
        id = "1",
        namaBarang = "Tempat Pensil",
        lokasi = "Perpustakaan SMKN 4 Bandung",
        waktu = "10:00 WIB, 07/10/2026",
        tipe = "Temuan",
        status = "Selesai",
        deskripsi = "Tempat pensil kain warna merah kotak-kotak, berisi pensil, pulpen, dan penggaris.",
        catatan = "Sudah diserahkan ke petugas perpustakaan.",
        imageType = "pencil"
    )

    private val walletReport = ReportItem(
        id = "2",
        namaBarang = "Dompet",
        lokasi = "Kantin SMKN 4 Bandung",
        waktu = "09:00 WIB, 26/09/2026",
        tipe = "Hilang",
        status = "Aktif",
        deskripsi = "Dompet berwarna hitam, ditemukan disekitaran taman depan ruang guru, isinya ada 3 kartu dan sejumlah uang yang cukup banyak",
        catatan = "Haii, kalau mau ngambil dompetnya saya tunggu di depan gerbang habis pulang sekolah",
        imageType = "wallet"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_laporan_saya)

        val mainView = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBackHeader = findViewById<ImageView>(R.id.btnBackHeader)
        btnBackHeader?.setOnClickListener {
            finish()
        }

        val btnTambahLaporan = findViewById<View>(R.id.btnTambahLaporan)
        btnTambahLaporan?.setOnClickListener {
            val intent = Intent(this, LaporBarangActivity::class.java)
            startActivity(intent)
        }

        setupCardClickListeners()
    }

    private fun setupCardClickListeners() {
        val cardItemPencil = findViewById<View>(R.id.cardItemPencil)
        val cardItemWallet = findViewById<View>(R.id.cardItemWallet)

        // Explicit Intent to DetailBarangActivity for Pencil Case Card
        cardItemPencil?.setOnClickListener {
            val intent = Intent(this, DetailBarangActivity::class.java).apply {
                putExtra("EXTRA_REPORT_ITEM", pencilReport)
            }
            startActivity(intent)
        }

        // Explicit Intent to DetailBarangActivity for Wallet Card
        cardItemWallet?.setOnClickListener {
            val intent = Intent(this, DetailBarangActivity::class.java).apply {
                putExtra("EXTRA_REPORT_ITEM", walletReport)
            }
            startActivity(intent)
        }
    }
}
