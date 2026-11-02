package com.example.FrnsMobile_Bangunan.projek

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.FrnsMobile_Bangunan.R
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var rvKategori: RecyclerView
    private lateinit var rvFlashSale: RecyclerView
    private lateinit var rvProduk: RecyclerView
    private lateinit var etSearch: EditText

    // Register Activity Result Launcher untuk Voice Search
    private val voiceLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            spokenText?.let {
                etSearch.setText(it)
                Toast.makeText(requireContext(), "Mencari suara: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvKategori  = view.findViewById(R.id.rvKategori)
        rvFlashSale = view.findViewById(R.id.rvFlashSale)
        rvProduk    = view.findViewById(R.id.rvProduk)
        etSearch    = view.findViewById(R.id.etSearch)

        etSearch.setOnEditorActionListener { _, _, _ ->
            Toast.makeText(requireContext(), "Mencari: ${etSearch.text}", Toast.LENGTH_SHORT).show()
            false
        }

        // Event listener klik tombol Voice Search
        view.findViewById<ImageButton>(R.id.ibVoiceSearch).setOnClickListener {
            startVoiceRecognition()
        }

        view.findViewById<ImageView>(R.id.ivCart).setOnClickListener {
            Toast.makeText(requireContext(), "Buka Keranjang", Toast.LENGTH_SHORT).show()
        }
        view.findViewById<ImageView>(R.id.ivNotif).setOnClickListener {
            Toast.makeText(requireContext(), "Buka Notifikasi", Toast.LENGTH_SHORT).show()
        }

        setupKategori()
        setupFlashSale()
        setupProduk()
    }

    private fun startVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Sebutkan material bangunan...")
        }

        try {
            voiceLauncher.launch(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Fitur Voice Search tidak didukung pada HP Anda", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupKategori() {
        val listKategori = listOf(
            KategoriModel("Semen", android.R.drawable.ic_menu_agenda),
            KategoriModel("Besi", android.R.drawable.ic_menu_sort_by_size),
            KategoriModel("Cat", android.R.drawable.ic_menu_edit),
            KategoriModel("Kayu", android.R.drawable.ic_menu_gallery),
            KategoriModel("Pipa", android.R.drawable.ic_menu_compass),
            KategoriModel("Keramik", android.R.drawable.ic_menu_view),
            KategoriModel("Pasir", android.R.drawable.ic_menu_mapmode),
            KategoriModel("Bata", android.R.drawable.ic_menu_save)
        )
        rvKategori.layoutManager = GridLayoutManager(requireContext(), 4)
        rvKategori.adapter = KategoriAdapter(listKategori) {
            Toast.makeText(requireContext(), "Kategori: ${it.nama}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupFlashSale() {
        val listFlashSale = listOf(
            ProdukModel(
                nama = "Semen Tiga Roda 50kg",
                harga = "Rp 65.000",
                hargaCoret = "Rp 80.000",
                diskon = "18%",
                gambarResId = R.drawable.semen_3_rode,
                isFlashSale = true
            ),
            ProdukModel(
                nama = "Cat Tembok Dulux 5kg",
                harga = "Rp 120.000",
                hargaCoret = "Rp 150.000",
                diskon = "20%",
                gambarResId = android.R.drawable.ic_menu_gallery,
                isFlashSale = true
            ),
            ProdukModel(
                nama = "Besi Beton 10mm",
                harga = "Rp 85.000",
                hargaCoret = "Rp 100.000",
                diskon = "15%",
                gambarResId = android.R.drawable.ic_menu_gallery,
                isFlashSale = true
            ),
            ProdukModel(
                nama = "Pipa PVC 3 inch",
                harga = "Rp 45.000",
                hargaCoret = "Rp 55.000",
                diskon = "10%",
                gambarResId = android.R.drawable.ic_menu_gallery,
                isFlashSale = true
            )
        )
        rvFlashSale.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvFlashSale.adapter = ProdukAdapter(listFlashSale) {
            Toast.makeText(requireContext(), "Beli: ${it.nama}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupProduk() {
        val listProduk = listOf(
            ProdukModel(
                nama = "Semen Gresik 40kg",
                harga = "Rp 58.000",
                gambarResId = android.R.drawable.ic_menu_gallery,
                rating = "4.9",
                terjual = "500+"
            ),
            ProdukModel(
                nama = "Besi Ulir 12mm SNI",
                harga = "Rp 95.000",
                gambarResId = android.R.drawable.ic_menu_gallery,
                rating = "4.8",
                terjual = "320+"
            ),
            ProdukModel(
                nama = "Cat Avian 1kg",
                harga = "Rp 45.000",
                gambarResId = android.R.drawable.ic_menu_gallery,
                rating = "4.7",
                terjual = "280+"
            ),
            ProdukModel(
                nama = "Keramik Roman 40x40",
                harga = "Rp 55.000",
                gambarResId = android.R.drawable.ic_menu_gallery,
                rating = "4.9",
                terjual = "410+"
            ),
            ProdukModel(
                nama = "Pipa Paralon 2 inch",
                harga = "Rp 35.000",
                gambarResId = android.R.drawable.ic_menu_gallery,
                rating = "4.6",
                terjual = "150+"
            ),
            ProdukModel(
                nama = "Triplek 9mm",
                harga = "Rp 110.000",
                gambarResId = android.R.drawable.ic_menu_gallery,
                rating = "4.8",
                terjual = "220+"
            )
        )
        rvProduk.layoutManager = GridLayoutManager(requireContext(), 2)
        rvProduk.adapter = ProdukAdapter(listProduk) {
            Toast.makeText(requireContext(), "Beli: ${it.nama}", Toast.LENGTH_SHORT).show()
        }
    }
}