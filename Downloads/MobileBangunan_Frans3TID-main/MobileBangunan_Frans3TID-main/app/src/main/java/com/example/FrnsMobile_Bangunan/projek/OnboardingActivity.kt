package com.example.FrnsMobile_Bangunan.projek

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.FrnsMobile_Bangunan.R
import com.google.android.material.button.MaterialButton

data class OnboardItem(val title: String, val desc: String, val imageRes: Int)

class OnboardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val viewPager = findViewById<ViewPager2>(R.id.viewPagerOnboarding)
        val btnNext = findViewById<MaterialButton>(R.id.btnNextOnboarding)
        val tvSkip = findViewById<TextView>(R.id.tvSkip)

        val slides = listOf(
            OnboardItem(
                "Material Bangunan Terlengkap",
                "Belanja semen, besi beton, cat, hingga keramik berkualitas langsung dari Toko Bangunan Frans.",
                R.drawable.bgunanni
            ),
            OnboardItem(
                "Pencarian Suara Praktis",
                "Cari kebutuhan material proyek lebih cepat tanpa mengetik menggunakan fitur Voice Search.",
                android.R.drawable.ic_btn_speak_now
            ),
            OnboardItem(
                "Cetak Nota PDF Resmi",
                "Unduh dan simpan bukti transaksi belanja material Anda ke dalam format PDF kapan saja.",
                android.R.drawable.ic_menu_save
            )
        )

        viewPager.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_onboarding, parent, false)
                return object : RecyclerView.ViewHolder(view) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                val item = slides[position]
                holder.itemView.findViewById<TextView>(R.id.tvOnboardTitle).text = item.title
                holder.itemView.findViewById<TextView>(R.id.tvOnboardDesc).text = item.desc
                holder.itemView.findViewById<ImageView>(R.id.ivOnboard).setImageResource(item.imageRes)
            }

            override fun getItemCount() = slides.size
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                btnNext.text = if (position == slides.size - 1) "Mulai Sekarang" else "Lanjut"
            }
        })

        btnNext.setOnClickListener {
            if (viewPager.currentItem + 1 < slides.size) {
                viewPager.currentItem += 1
            } else {
                pindahKeLogin()
            }
        }

        tvSkip.setOnClickListener {
            pindahKeLogin()
        }
    }

    private fun pindahKeLogin() {
        startActivity(Intent(this, loginActivity::class.java))
        finish()
    }
}