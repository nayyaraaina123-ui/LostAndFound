package com.rpl1.lostnfound

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class DetailBarangActivity : AppCompatActivity() {

    private var reportItem: ReportItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_barang)

        val mainView = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve Extra ReportItem via Explicit Intent
        reportItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra("EXTRA_REPORT_ITEM", ReportItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra("EXTRA_REPORT_ITEM") as? ReportItem
        }

        setupViews(mainView)
    }

    private fun setupViews(rootView: View) {
        val imgHeader = findViewById<ImageView>(R.id.imgDetailHeader)
        val tvCategoryPill = findViewById<TextView>(R.id.tvCategoryPill)
        val tvDeskripsiDetail = findViewById<TextView>(R.id.tvDeskripsiDetail)
        val tvNoteDetail = findViewById<TextView>(R.id.tvNoteDetail)
        val btnSudahDikembalikan = findViewById<Button>(R.id.btnSudahDikembalikan)

        // Populate Data if present
        reportItem?.let { item ->
            tvCategoryPill.text = "✦ ${item.namaBarang}"
            tvDeskripsiDetail.text = item.deskripsi
            tvNoteDetail.text = if (item.catatan.isNotEmpty()) item.catatan else "-"

            if (item.imageType == "pencil") {
                imgHeader.setImageResource(R.drawable.sample_tempat_pensil)
            } else {
                imgHeader.setImageResource(R.drawable.sample_dompet)
            }
        }

        btnSudahDikembalikan.setOnClickListener {
            // Toast feedback
            Toast.makeText(this, "Barang telah dikembalikan!", Toast.LENGTH_SHORT).show()

            // Snackbar feedback with Action button using Explicit Intent
            Snackbar.make(rootView, "Status barang telah diperbarui menjadi Selesai!", Snackbar.LENGTH_LONG)
                .setAction("KEMBALI KE UTAMA") {
                    val intent = Intent(this, LaporanSayaActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    }
                    startActivity(intent)
                    finish()
                }
                .show()
        }
    }
}
