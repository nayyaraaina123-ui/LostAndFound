package com.rpl1.lostnfound

import java.io.Serializable

data class ReportItem(
    val id: String,
    var namaBarang: String,
    var lokasi: String,
    val waktu: String,
    var tipe: String, // "Hilang" or "Temuan"
    var status: String, // "Aktif" or "Selesai"
    var deskripsi: String,
    var catatan: String,
    val imageType: String // "pencil" or "wallet"
) : Serializable
