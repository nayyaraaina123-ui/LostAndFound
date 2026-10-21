package com.rpl1.lostnfound

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.jvm.java

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageView
    private lateinit var etCari: EditText
    private lateinit var rgTab: RadioGroup
    private lateinit var rvBarang: RecyclerView
    private lateinit var btnLapor: Button
    private lateinit var adapter: BarangAdapter

    private lateinit var btnBackDrawer: ImageView
    private lateinit var navBeranda: TextView
    private lateinit var navLaporanSaya: TextView

    private var tabAktif = "Semua"

    private val semuaBarang = listOf(
        Barang("Tempat Pensil", "Perpustakaan", "Temuan", "10:00 WIB, 07/10/2026", R.drawable.foto_pensil),
        Barang("Dompet", "Kantin", "Hilang", "09:00 WIB, 26/09/2026", R.drawable.foto_dompet)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)
        btnMenu = findViewById(R.id.btnMenu)
        etCari = findViewById(R.id.etCari)
        rgTab = findViewById(R.id.rgTab)
        rvBarang = findViewById(R.id.rvBarang)
        btnLapor = findViewById(R.id.btnLapor)

        btnBackDrawer = findViewById(R.id.btnBackDrawer)
        navBeranda = findViewById(R.id.navBeranda)
        navLaporanSaya = findViewById(R.id.navLaporanSaya)

        adapter = BarangAdapter(semuaBarang) { barang -> bukaDetail(barang) }
        rvBarang.layoutManager = LinearLayoutManager(this)
        rvBarang.adapter = adapter

        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        btnBackDrawer.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        navBeranda.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            Toast.makeText(this, "Menampilkan Beranda", Toast.LENGTH_SHORT).show()
        }

        navLaporanSaya.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            Snackbar.make(btnLapor, "Halaman Laporan Saya dikerjakan anggota lain", Snackbar.LENGTH_LONG)
                .setAnchorView(btnLapor)
                .setAction("OK") { }
                .show()
        }

        rgTab.setOnCheckedChangeListener { _, checkedId ->
            tabAktif = when (checkedId) {
                R.id.rbHilang -> "Hilang"
                R.id.rbTemuan -> "Temuan"
                else -> "Semua"
            }
            Toast.makeText(this, "Menampilkan: $tabAktif", Toast.LENGTH_SHORT).show()
            terapkanFilter()
        }

        etCari.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
            override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {
                terapkanFilter()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        etCari.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Toast.makeText(this, "Mencari: ${etCari.text}", Toast.LENGTH_SHORT).show()
                true
            } else false
        }

        btnLapor.setOnClickListener {
            Snackbar.make(it, "Halaman Lapor dikerjakan anggota lain", Snackbar.LENGTH_LONG)
                .setAnchorView(btnLapor)
                .setAction("OK") { }
                .show()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun terapkanFilter() {
        val kataKunci = etCari.text.toString().trim()

        val hasil = semuaBarang.filter {
            val cocokTab = (tabAktif == "Semua") || it.status == tabAktif
            val cocokNama = it.nama.contains(kataKunci, ignoreCase = true)
            cocokTab && cocokNama
        }
        adapter.updateData(hasil)

        if (hasil.isEmpty()) {
            Snackbar.make(rvBarang, "Barang tidak ditemukan", Snackbar.LENGTH_LONG)
                .setAction("Reset") {
                    etCari.setText("")
                    rgTab.check(R.id.rbSemua)
                }
                .show()
        }
    }

    private fun bukaDetail(barang: Barang) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_NAMA, barang.nama)
        intent.putExtra(DetailActivity.EXTRA_LOKASI, barang.lokasi)
        intent.putExtra(DetailActivity.EXTRA_STATUS, barang.status)
        intent.putExtra(DetailActivity.EXTRA_WAKTU, barang.waktu)
        intent.putExtra(DetailActivity.EXTRA_GAMBAR, barang.gambar)

        Toast.makeText(this, "Membuka detail ${barang.nama}", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }
}