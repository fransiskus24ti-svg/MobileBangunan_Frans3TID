package com.example.FrnsMobile_Bangunan.projek

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.FrnsMobile_Bangunan.R
import com.google.android.material.card.MaterialCardView
import java.text.NumberFormat
import java.util.Locale

class KeranjangFragment : Fragment() {

    private lateinit var containerItem: LinearLayout
    private lateinit var tvTotalHarga: TextView
    private lateinit var tvJumlahItem: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_keranjang, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        containerItem = view.findViewById(R.id.containerItemKeranjang)
        tvTotalHarga  = view.findViewById(R.id.tvTotalHargaCart)
        tvJumlahItem  = view.findViewById(R.id.tvJumlahItemCart)

        view.findViewById<View>(R.id.btnBackCart).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        view.findViewById<View>(R.id.btnCheckoutCart).setOnClickListener {
            if (CartManager.daftarKeranjang.isEmpty()) {
                Toast.makeText(requireContext(), "Keranjang masih kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(requireContext(), "Pesanan berhasil dibuat!", Toast.LENGTH_SHORT).show()
            val parentId = (view.parent as? ViewGroup)?.id ?: View.NO_ID
            val targetContainerId = if (parentId != View.NO_ID) parentId else android.R.id.content

            parentFragmentManager.beginTransaction()
                .replace(targetContainerId, DetailTransaksiFragment())
                .addToBackStack(null)
                .commit()
        }

        tampilkanDaftarKeranjang()
    }

    private fun tampilkanDaftarKeranjang() {
        containerItem.removeAllViews()
        val daftar = CartManager.daftarKeranjang

        tvJumlahItem.text = "${daftar.size} Jenis"
        val formatRupiah = NumberFormat.getNumberInstance(Locale("in", "ID"))
        tvTotalHarga.text = "Rp ${formatRupiah.format(CartManager.hitungTotalHarga())}"

        if (daftar.isEmpty()) {
            val tvKosong = TextView(requireContext()).apply {
                text = "Keranjang belanja kamu masih kosong.\nYuk pilih material bangunan dulu!"
                textSize = 14f
                setTextColor(Color.parseColor("#64748B"))
                gravity = Gravity.CENTER
                setPadding(0, 120, 0, 0)
            }
            containerItem.addView(tvKosong)
            return
        }

        for (item in daftar) {
            val card = MaterialCardView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = 28 }
                radius = 36f
                cardElevation = 3f
                setCardBackgroundColor(Color.WHITE)
                strokeColor = Color.parseColor("#E2E8F0")
                strokeWidth = 2
            }

            val row = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(32, 32, 32, 32)
            }

            val ivFoto = ImageView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(130, 130).apply { marginEnd = 24 }
                setImageResource(item.produk.gambarResId)
                setBackgroundColor(Color.parseColor("#EFF6FF"))
                setPadding(20, 20, 20, 20)
            }

            val infoCol = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            }

            val tvNama = TextView(requireContext()).apply {
                text = item.produk.nama
                textSize = 14f
                setTypeface(null, Typeface.BOLD)
                setTextColor(Color.parseColor("#0F172A"))
            }

            val tvHarga = TextView(requireContext()).apply {
                text = "${item.produk.harga}  ×  ${item.jumlah}"
                textSize = 13f
                setTypeface(null, Typeface.BOLD)
                setTextColor(Color.parseColor("#0038A8"))
                setPadding(0, 8, 0, 0)
            }

            infoCol.addView(tvNama)
            infoCol.addView(tvHarga)

            // Wadah Tombol [ - ]  jumlah  [ + ]
            val qtyBox = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
            }

            val btnKurang = TextView(requireContext()).apply {
                text = " − "
                textSize = 16f
                setTypeface(null, Typeface.BOLD)
                setTextColor(Color.parseColor("#0F172A"))
                setBackgroundColor(Color.parseColor("#E2E8F0"))
                setPadding(22, 10, 22, 10)
                setOnClickListener {
                    if (item.jumlah > 1) {
                        item.jumlah -= 1
                    } else {
                        daftar.remove(item)
                        Toast.makeText(requireContext(), "${item.produk.nama} dihapus", Toast.LENGTH_SHORT).show()
                    }
                    tampilkanDaftarKeranjang()
                }
            }

            val tvQty = TextView(requireContext()).apply {
                text = "${item.jumlah}"
                textSize = 14f
                setTypeface(null, Typeface.BOLD)
                setTextColor(Color.parseColor("#0F172A"))
                setPadding(24, 0, 24, 0)
            }

            val btnTambah = TextView(requireContext()).apply {
                text = " + "
                textSize = 16f
                setTypeface(null, Typeface.BOLD)
                setTextColor(Color.WHITE)
                setBackgroundColor(Color.parseColor("#0038A8"))
                setPadding(22, 10, 22, 10)
                setOnClickListener {
                    item.jumlah += 1
                    tampilkanDaftarKeranjang()
                }
            }

            qtyBox.addView(btnKurang)
            qtyBox.addView(tvQty)
            qtyBox.addView(btnTambah)

            row.addView(ivFoto)
            row.addView(infoCol)
            row.addView(qtyBox)
            card.addView(row)
            containerItem.addView(card)
        }
    }
}