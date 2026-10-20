package com.example.FrnsMobile_Bangunan.projek

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.example.FrnsMobile_Bangunan.R

class VerifikasiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Pastikan Anda sudah membuat layout activity_verifikasi.xml
        setContentView(R.layout.activity_verifikasi)

        // Ganti ID sesuai dengan yang ada di file XML Anda
        val etOtp = findViewById<EditText>(R.id.etOtp)
        val btnVerifikasi = findViewById<Button>(R.id.btnVerifikasi)

        // Mengambil data yang dikirim dari RegisterActivity
        val registeredNoHp = intent.getStringExtra("EXTRA_NOHP")
        val registeredUsername = intent.getStringExtra("EXTRA_USERNAME")
        val registeredPassword = intent.getStringExtra("EXTRA_PASSWORD")

        btnVerifikasi.setOnClickListener {
            val otpInput = etOtp.text.toString().trim()

            // Jika OTP berbeda dengan No HP atau kosong, tampilkan MaterialAlertDialog
            if (otpInput.isEmpty() || otpInput != registeredNoHp) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Verifikasi Gagal")
                    .setMessage("Kode OTP salah atau kosong.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            } else {
                // Jika proses verifikasi berhasil dilewati:
                // Simpan data ke SharedPreferences
                val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("USERNAME", registeredUsername)
                editor.putString("PASSWORD", registeredPassword)
                editor.apply()

                Toast.makeText(this, "Verifikasi Sukses!", Toast.LENGTH_SHORT).show()

                // Arahkan ke halaman login
                val intentLogin = Intent(this@VerifikasiActivity, loginActivity::class.java)
                startActivity(intentLogin)
                finish()
            }
        }
    }
}