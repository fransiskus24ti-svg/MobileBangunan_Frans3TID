package com.example.FrnsMobile_Bangunan.projek

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.FrnsMobile_Bangunan.R
import com.google.android.material.switchmaterial.SwitchMaterial

class ProfilDeveloperFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_profil_developer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switchDarkMode = view.findViewById<SwitchMaterial>(R.id.switchDarkMode)

        // 1. Cek status mode saat ini untuk menentukan posisi awal Switch (ON/OFF)
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        switchDarkMode.isChecked = (currentNightMode == Configuration.UI_MODE_NIGHT_YES)

        // 2. Berikan listener ketika pengguna menggeser Switch
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Event Listener Tombol Sosial Media
        view.findViewById<View>(R.id.btnGithub).setOnClickListener {
            openUrl("https://github.com/rey24ti")
        }

        view.findViewById<View>(R.id.btnInstagram).setOnClickListener {
            openUrl("https://www.instagram.com/rey.vinsen/")
        }

        view.findViewById<View>(R.id.btnYoutube).setOnClickListener {
            openUrl("https://www.youtube.com/@REYVINSHEN")
        }
    }

    private fun openUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Tidak dapat membuka link: $url", Toast.LENGTH_SHORT).show()
        }
    }
}