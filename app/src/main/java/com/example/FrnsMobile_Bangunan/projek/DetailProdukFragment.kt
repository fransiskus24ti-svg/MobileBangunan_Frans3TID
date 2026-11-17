package com.example.FrnsMobile_Bangunan.projek

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.FrnsMobile_Bangunan.R

class DetailProdukFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_detail_produk, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nama = arguments?.getString("NAMA_PRODUK") ?: "Material Bangunan"
        val harga = arguments?.getString("HARGA_PRODUK") ?: "Rp 0"
        val rating = arguments?.getString("RATING_PRODUK") ?: "4.9"
        val terjual = arguments?.getString("TERJUAL_PRODUK") ?: "100+"
        val gambarResId = arguments?.getInt("GAMBAR_PRODUK", android.R.drawable.ic_menu_gallery)
            ?: android.R.drawable.ic_menu_gallery

        view.findViewById<TextView>(R.id.tvNamaProdukDetail).text = nama
        view.findViewById<TextView>(R.id.tvHargaProdukDetail).text = harga
        view.findViewById<TextView>(R.id.tvRatingTerjual).text = "★ $rating  •  Terjual $terjual unit"
        view.findViewById<ImageView>(R.id.ivGambarProduk).setImageResource(gambarResId)

        // Tombol Kembali
        view.findViewById<View>(R.id.btnBackDetail).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Tombol + Keranjang -> Menyimpan produk ke CartManager
        view.findViewById<View>(R.id.btnTambahKeranjang).setOnClickListener {
            val produkBaru = ProdukModel(
                nama = nama,
                harga = harga,
                gambarResId = gambarResId,
                rating = rating,
                terjual = terjual
            )
            CartManager.tambahProduk(produkBaru)
            Toast.makeText(requireContext(), "$nama ditambahkan ke keranjang!", Toast.LENGTH_SHORT).show()
        }

        // Tombol Beli Sekarang -> Lanjut ke DetailTransaksiFragment
        view.findViewById<View>(R.id.btnBeliSekarang).setOnClickListener {
            val bundle = Bundle().apply {
                putString("NAMA_PRODUK", nama)
                putString("HARGA_PRODUK", harga)
            }
            val fragmentTransaksi = DetailTransaksiFragment().apply {
                arguments = bundle
            }

            val parentId = (view.parent as? ViewGroup)?.id ?: View.NO_ID
            val targetContainerId = if (parentId != View.NO_ID) parentId else android.R.id.content

            parentFragmentManager.beginTransaction()
                .replace(targetContainerId, fragmentTransaksi)
                .addToBackStack(null)
                .commit()
        }
    }
}