package com.rpl1.lostnfound

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BarangAdapter(
    private var daftar: List<Barang>,
    private val onItemClick: (Barang) -> Unit
) : RecyclerView.Adapter<BarangAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgBarang)
        val nama: TextView = view.findViewById(R.id.tvNama)
        val lokasi: TextView = view.findViewById(R.id.tvLokasi)
        val waktu: TextView = view.findViewById(R.id.tvWaktu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_barang, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val b = daftar[position]
        holder.img.setImageResource(b.gambar)
        holder.nama.text = "— .✦${b.nama}"
        holder.lokasi.text = b.lokasi

        if (b.status == "Hilang") {
            holder.waktu.text = "Waktu Hilang :\n${b.waktu}"
            holder.waktu.setBackgroundResource(R.drawable.bg_tag_hilang)
        } else {
            holder.waktu.text = "Waktu Ditemukan :\n${b.waktu}"
            holder.waktu.setBackgroundResource(R.drawable.bg_tag_temuan)
        }

        holder.itemView.setOnClickListener { onItemClick(b) }
    }

    override fun getItemCount() = daftar.size

    fun updateData(baru: List<Barang>) {
        daftar = baru
        notifyDataSetChanged()
    }
}