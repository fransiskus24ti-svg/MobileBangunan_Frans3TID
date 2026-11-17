package com.example.FrnsMobile_Bangunan.projek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.FrnsMobile_Bangunan.R

class ProdukKategoriFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_produk_kategori, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val namaKategori = arguments?.getString("NAMA_KATEGORI") ?: "Semua Material"

        val tvJudul = view.findViewById<TextView>(R.id.tvJudulKategori)
        val tvSubInfo = view.findViewById<TextView>(R.id.tvSubInfo)
        val rvProduk = view.findViewById<RecyclerView>(R.id.rvProdukKategori)

        tvJudul.text = "Kategori: $namaKategori"

        // Menggunakan <View> agar aman untuk MaterialCardView maupun ImageView
        view.findViewById<View>(R.id.btnBack).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Tombol Keranjang di pojok kanan atas
        view.findViewById<ImageView>(R.id.ivCartKategori)?.setOnClickListener {
            Toast.makeText(requireContext(), "Membuka Keranjang...", Toast.LENGTH_SHORT).show()
        }

        val daftarProduk = ambilProdukByKategori(namaKategori)
        tvSubInfo.text = "Menampilkan ${daftarProduk.size} produk untuk kategori $namaKategori"

        rvProduk.layoutManager = GridLayoutManager(requireContext(), 2)
        rvProduk.adapter = ProdukAdapter(daftarProduk) { produk ->
            bukaDetailProduk(produk)
        }
    }

    private fun bukaDetailProduk(produk: ProdukModel) {
        val bundle = Bundle().apply {
            putString("NAMA_PRODUK", produk.nama)
            putString("HARGA_PRODUK", produk.harga)
            putString("RATING_PRODUK", produk.rating)
            putString("TERJUAL_PRODUK", produk.terjual)
            putInt("GAMBAR_PRODUK", produk.gambarResId)
        }

        val fragmentDetail = DetailProdukFragment().apply {
            arguments = bundle
        }

        val parentId = (view?.parent as? ViewGroup)?.id ?: View.NO_ID
        val targetContainerId = if (parentId != View.NO_ID) parentId else android.R.id.content

        parentFragmentManager.beginTransaction()
            .replace(targetContainerId, fragmentDetail)
            .addToBackStack(null)
            .commit()
    }

    private fun ambilProdukByKategori(kategori: String): List<ProdukModel> {
        return when (kategori.lowercase()) {
            "semen" -> listOf(
                ProdukModel("Semen Tiga Roda 50kg", "Rp 65.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.9", terjual = "850+"),
                ProdukModel("Semen Gresik 40kg", "Rp 58.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.9", terjual = "500+"),
                ProdukModel("Semen Padang 50kg", "Rp 63.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.8", terjual = "420+"),
                ProdukModel("Semen Putih Tiga Roda 40kg", "Rp 92.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.7", terjual = "190+")
            )
            "besi" -> listOf(
                ProdukModel("Besi Beton 10mm SNI", "Rp 85.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.8", terjual = "320+"),
                ProdukModel("Besi Ulir 12mm SNI", "Rp 95.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.9", terjual = "410+"),
                ProdukModel("Baja Ringan Kanal C75", "Rp 78.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.8", terjual = "600+")
            )
            "cat" -> listOf(
                ProdukModel("Cat Tembok Dulux 5kg", "Rp 120.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.9", terjual = "310+"),
                ProdukModel("Cat Avian Kayu & Besi 1kg", "Rp 45.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.7", terjual = "280+"),
                ProdukModel("Cat Pelapis Anti Bocor 4kg", "Rp 165.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.8", terjual = "240+")
            )
            "kayu" -> listOf(
                ProdukModel("Triplek Meranti 9mm", "Rp 110.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.8", terjual = "220+"),
                ProdukModel("Kayu Kaso Borneo 4x6", "Rp 32.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.7", terjual = "450+")
            )
            "pipa" -> listOf(
                ProdukModel("Pipa PVC Rucika 3 inch", "Rp 45.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.9", terjual = "510+"),
                ProdukModel("Pipa Paralon 2 inch", "Rp 35.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.6", terjual = "150+")
            )
            "keramik" -> listOf(
                ProdukModel("Keramik Roman 40x40 Putih", "Rp 55.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.9", terjual = "410+"),
                ProdukModel("Granit Lantai 60x60 Glossy", "Rp 135.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.8", terjual = "290+")
            )
            else -> listOf(
                ProdukModel("$kategori Standar Proyek", "Rp 55.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.8", terjual = "200+"),
                ProdukModel("$kategori Kualitas Premium", "Rp 85.000", gambarResId = android.R.drawable.ic_menu_gallery, rating = "4.9", terjual = "150+")
            )
        }
    }
}