package com.example.FrnsMobile_Bangunan.projek

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.FrnsMobile_Bangunan.R

class loginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText // Ini digunakan sebagai Username
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView
    private lateinit var tvLupaPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        setupListener()
    }

    private fun initView() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvDaftarSekarang)
        tvLupaPassword = findViewById(R.id.tvLupaPassword)

        etEmail.hint = "Masukkan Username Anda" // Disesuaikan dengan kebutuhan soal
        etEmail.inputType = android.text.InputType.TYPE_CLASS_TEXT
    }

    private fun setupListener() {
        btnLogin.setOnClickListener {
            val nama = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            when {
                nama.isEmpty() -> {
                    etEmail.error = "Username tidak boleh kosong"
                    etEmail.requestFocus()
                }
                password.isEmpty() -> {
                    etPassword.error = "Password tidak boleh kosong"
                    etPassword.requestFocus()
                }
                else -> {
                    prosesLogin(nama, password)
                }
            }
        }

        etPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                btnLogin.performClick()
                true
            } else {
                false
            }
        }

        tvRegister.setOnClickListener {
            val intent = Intent(this@loginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        tvLupaPassword.setOnClickListener {
            Toast.makeText(this, "Buka halaman Lupa Password", Toast.LENGTH_SHORT).show()
        }
    }

    private fun prosesLogin(usernameInput: String, passwordInput: String) {
        btnLogin.isEnabled = false
        btnLogin.text = "Loading..."

        btnLogin.postDelayed({
            // Ambil data yang tersimpan dari SharedPreferences (Dibuat di halaman OTP)
            val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            val savedUsername = sharedPreferences.getString("USERNAME", null)
            val savedPassword = sharedPreferences.getString("PASSWORD", null)

            // Aturan 1: Boleh login jika username sama dengan password
            val rule1 = (usernameInput == passwordInput)

            // Aturan 2: Boleh login jika username dan password sesuai dengan yang ada di SharedPreferences
            val rule2 = (usernameInput == savedUsername && passwordInput == savedPassword)

            if (rule1 || (rule2 && savedUsername != null)) {
                Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                hideKeyboard()

                // Arahkan ke halaman utama aplikasi Anda (beradaActivity)
                val intent = Intent(this@loginActivity, beradaActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login Gagal! Username atau password salah.", Toast.LENGTH_LONG).show()
                etEmail.error = "Cek kembali data Anda"
                etEmail.requestFocus()

                btnLogin.isEnabled = true
                btnLogin.text = "LOGIN"
            }
        }, 1000)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}