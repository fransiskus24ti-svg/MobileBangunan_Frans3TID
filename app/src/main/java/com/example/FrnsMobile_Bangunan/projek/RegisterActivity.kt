package com.example.FrnsMobile_Bangunan.projek

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.FrnsMobile_Bangunan.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etNamaDepan = findViewById<EditText>(R.id.etNama)
        val etNamaBelakang = findViewById<EditText>(R.id.etNamaBelakang)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etNoHp = findViewById<EditText>(R.id.etNoHp)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etKonfirmasiPassword = findViewById<EditText>(R.id.etKonfirmasiPassword)
        val btnDaftar = findViewById<Button>(R.id.btnDaftar)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        btnDaftar.setOnClickListener {
            val namaDepan = etNamaDepan.text.toString().trim()
            val namaBelakang = etNamaBelakang.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val noHp = etNoHp.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val konfirmasi = etKonfirmasiPassword.text.toString().trim()

            val namaLengkap = "$namaDepan $namaBelakang".trim()
            val username = email

            if (namaDepan.isEmpty() || email.isEmpty() || noHp.isEmpty() || password.isEmpty() || konfirmasi.isEmpty()) {
                Toast.makeText(this, "Mohon lengkapi semua data!", Toast.LENGTH_SHORT).show()
            } else if (password != konfirmasi) {
                Toast.makeText(this, "Kata sandi dan Konfirmasi tidak sama!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this@RegisterActivity, VerifikasiActivity::class.java)
                intent.putExtra("EXTRA_NAMA", namaLengkap)
                intent.putExtra("EXTRA_NOHP", noHp)
                intent.putExtra("EXTRA_USERNAME", username)
                intent.putExtra("EXTRA_PASSWORD", password)
                startActivity(intent)
            }
        }

        tvLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, loginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}