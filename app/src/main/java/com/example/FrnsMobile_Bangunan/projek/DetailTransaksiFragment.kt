package com.example.FrnsMobile_Bangunan.projek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.FrnsMobile_Bangunan.R
import java.text.NumberFormat
import java.util.Locale

class DetailTransaksiFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_detail_transaksi, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val formatRupiah = NumberFormat.getNumberInstance(Locale("in", "ID"))

        // Cek apakah datang dari tombol "Beli Sekarang" (1 produk) atau dari "Keranjang"
        val namaLangsung = arguments?.getString("NAMA_PRODUK")
        val hargaLangsungStr = arguments?.getString("HARGA_PRODUK")

        val subtotal: Int
        val namaTampil: String
        val subKeterangan: String
        val rincianQty: String

        if (namaLangsung != null && hargaLangsungStr != null) {
            // Dari tombol "Beli Sekarang" di DetailProdukFragment
            subtotal = hargaLangsungStr.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
            namaTampil = namaLangsung
            subKeterangan = "Material Konstruksi SNI • WestBuild"
            rincianQty = "1 unit × $hargaLangsungStr"
        } else {
            // Dari tombol "Buat Pesanan" di KeranjangFragment (CartManager)
            subtotal = CartManager.hitungTotalHarga()
            val daftar = CartManager.daftarKeranjang
            if (daftar.isNotEmpty()) {
                val itemPertama = daftar[0]
                namaTampil = if (daftar.size > 1) {
                    "${itemPertama.produk.nama} (+${daftar.size - 1} produk lainnya)"
                } else {
                    itemPertama.produk.nama
                }
                subKeterangan = "Total ${daftar.sumOf { it.jumlah }} barang dari Keranjang Belanja"
                rincianQty = "${daftar.size} jenis material bangunan"
            } else {
                namaTampil = "Pesanan Material WestBuild"
                subKeterangan = "Tipe 1 PCC - Kualitas Tinggi"
                rincianQty = "1 paket"
            }
        }

        val ongkir = 75000
        val asuransi = 2000
        val totalAkhir = subtotal + ongkir + asuransi

        // Masukkan data dinamis ke tampilan struk
        view.findViewById<TextView>(R.id.tvNamaProdukTransaksi)?.text = namaTampil
        view.findViewById<TextView>(R.id.tvSubProdukTransaksi)?.text = subKeterangan
        view.findViewById<TextView>(R.id.tvQtyHargaTransaksi)?.text = rincianQty
        view.findViewById<TextView>(R.id.tvHargaItemTransaksi)?.text =
            "Rp ${formatRupiah.format(subtotal)}"

        view.findViewById<TextView>(R.id.tvSubtotalTransaksi)?.text =
            "Rp ${formatRupiah.format(subtotal)}"
        view.findViewById<TextView>(R.id.tvOngkirTransaksi)?.text =
            "Rp ${formatRupiah.format(ongkir)}"
        view.findViewById<TextView>(R.id.tvAsuransiTransaksi)?.text =
            "Rp ${formatRupiah.format(asuransi)}"
        view.findViewById<TextView>(R.id.tvTotalBayarTransaksi)?.text =
            "Rp ${formatRupiah.format(totalAkhir)}"

        // Tombol Back
        view.findViewById<View>(R.id.btnBack)?.setOnClickListener {
            parentFragmentManager.popBackStack()

            // Tombol Chat Penjual
            view.findViewById<View>(R.id.btnChatPenjual)?.setOnClickListener {
                Toast.makeText(requireContext(), "Membuka Chat Penjual...", Toast.LENGTH_SHORT)
                    .show()
            }

            // Tombol Beli Lagi
            view.findViewById<View>(R.id.btnBeliLagi)?.setOnClickListener {
                Toast.makeText(requireContext(), "Menambahkan ke Keranjang...", Toast.LENGTH_SHORT)
                    .show()
            }

            // Tombol Lacak Pesanan
            view.findViewById<View>(R.id.btnLacak)?.setOnClickListener {
                Toast.makeText(requireContext(), "Melacak Pesanan...", Toast.LENGTH_SHORT).show()
            }

            // Tombol CS
            view.findViewById<View>(R.id.btnSuperCS)?.setOnClickListener {
                Toast.makeText(requireContext(), "Menghubungi CS...", Toast.LENGTH_SHORT).show()
            }
        }
    }
}