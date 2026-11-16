package com.example.FrnsMobile_Bangunan.projek

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.FrnsMobile_Bangunan.R

data class LayananModel(val nama: String, val iconRes: Int)

class LayananCustomAdapter(
    private val context: Context,
    private val listLayanan: List<LayananModel>
) : BaseAdapter() {

    override fun getCount(): Int = listLayanan.size
    override fun getItem(position: Int): Any = listLayanan[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_kategori, parent, false)

        val item = listLayanan[position]
        val ivIcon = view.findViewById<ImageView>(R.id.ivKategori)
        val tvNama = view.findViewById<TextView>(R.id.tvNamaKategori)

        ivIcon.setImageResource(item.iconRes)
        tvNama.text = item.nama

        return view
    }
}