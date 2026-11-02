package com.example.FrnsMobile_Bangunan

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.FrnsMobile_Bangunan.databinding.ActivityMainBinding
import com.example.FrnsMobile_Bangunan.projek.beradaActivity
import com.example.FrnsMobile_Bangunan.projek.loginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tombol 1: Membuka loginActivity
        binding.btnProjekUas.setOnClickListener {
            val intent = Intent(this@MainActivity, loginActivity::class.java)
            startActivity(intent)
        }

        // Tombol 2: Membuka beradaActivity
        binding.btnpelangg.setOnClickListener {
            val intent = Intent(this@MainActivity, beradaActivity::class.java)
            startActivity(intent)
        }

        // Tombol 3: Toggle Light / Dark Mode
        binding.btnMyProject.setOnClickListener {
            toggleDarkMode()
        }
    }

    private fun toggleDarkMode() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            // Jika sedang Mode Gelap, ganti ke Mode Terang
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            // Jika sedang Mode Terang, ganti ke Mode Gelap
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}