package com.example.FrnsMobile_Bangunan.projek

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.FrnsMobile_Bangunan.R
import com.google.android.material.card.MaterialCardView

class RuangChatFragment : Fragment() {

    private lateinit var containerBubble: LinearLayout
    private lateinit var scrollChat: ScrollView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_ruang_chat, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        containerBubble = view.findViewById(R.id.containerBubbleChat)
        scrollChat      = view.findViewById(R.id.scrollChat)
        val etPesan     = view.findViewById<EditText>(R.id.etPesanBaru)

        val infoPesanan = arguments?.getString("INFO_PESANAN") ?: "Pesanan Material WestBuild"

        view.findViewById<View>(R.id.btnBackRuangChat).setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Sambutan otomatis dari Admin Toko beserta detail pesanan
        tambahBubble(
            pengirimAdmin = true,
            teks = "Halo Mandor! Terima kasih telah memesan $infoPesanan di TB WestBuild (Pusat). Ada yang bisa kami bantu terkait pengiriman ke lokasi proyek?"
        )

        view.findViewById<View>(R.id.btnKirimPesanSekarang).setOnClickListener {
            val isi = etPesan.text.toString().trim()
            if (isi.isNotEmpty()) {
                tambahBubble(pengirimAdmin = false, teks = isi)
                etPesan.setText("")

                // Balasan otomatis dari admin toko
                tambahBubble(
                    pengirimAdmin = true,
                    teks = "Baik, pesan sudah kami terima dan diteruskan ke tim armada pengiriman gudang. Estimasi tiba hari ini."
                )
            }
        }
    }

    private fun tambahBubble(pengirimAdmin: Boolean, teks: String) {
        val wrapper = LinearLayout(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = 20 }
            gravity = if (pengirimAdmin) Gravity.START else Gravity.END
        }

        val card = MaterialCardView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            radius = 32f
            cardElevation = 2f
            setCardBackgroundColor(if (pengirimAdmin) Color.WHITE else Color.parseColor("#0038A8"))
            strokeColor = Color.parseColor("#E2E8F0")
            strokeWidth = if (pengirimAdmin) 2 else 0
        }

        val tvPesan = TextView(requireContext()).apply {
            text = teks
            textSize = 13f
            maxWidth = 720
            setTextColor(if (pengirimAdmin) Color.parseColor("#0F172A") else Color.WHITE)
            setPadding(36, 26, 36, 26)
        }

        card.addView(tvPesan)
        wrapper.addView(card)
        containerBubble.addView(wrapper)

        scrollChat.post { scrollChat.fullScroll(View.FOCUS_DOWN) }
    }
}