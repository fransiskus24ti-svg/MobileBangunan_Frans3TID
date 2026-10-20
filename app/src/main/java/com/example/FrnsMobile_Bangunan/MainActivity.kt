package com.example.FrnsMobile_Bangunan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.FrnsMobile_Bangunan.databinding.ActivityMainBinding
import com.example.FrnsMobile_Bangunan.projek.loginActivity
import com.example.FrnsMobile_Bangunan.projek.beradaActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnProjekUas.setOnClickListener {
            val intent = Intent(this@MainActivity, loginActivity::class.java)
            startActivity(intent)
        }

        binding.btnpelangg.setOnClickListener {
            val intent = Intent(this@MainActivity, beradaActivity::class.java)
            startActivity(intent)
        }
    }
}