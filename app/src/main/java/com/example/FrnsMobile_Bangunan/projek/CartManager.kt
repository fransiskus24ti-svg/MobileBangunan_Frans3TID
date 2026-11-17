package com.example.FrnsMobile_Bangunan.projek

data class CartItemModel(
    val produk: ProdukModel,
    var jumlah: Int = 1
)

object CartManager {
    val daftarKeranjang = mutableListOf(
        // Contoh 2 item awal agar keranjang langsung terlihat berisi saat dibuka
        CartItemModel(
            ProdukModel("Semen Tiga Roda 50kg", "Rp 65.000", gambarResId = android.R.drawable.ic_menu_gallery),
            jumlah = 2
        ),
        CartItemModel(
            ProdukModel("Besi Beton 10mm SNI", "Rp 85.000", gambarResId = android.R.drawable.ic_menu_gallery),
            jumlah = 5
        )
    )

    fun tambahProduk(produk: ProdukModel) {
        val itemAda = daftarKeranjang.find { it.produk.nama == produk.nama }
        if (itemAda != null) {
            itemAda.jumlah += 1
        } else {
            daftarKeranjang.add(CartItemModel(produk, 1))
        }
    }

    fun hitungTotalHarga(): Int {
        var total = 0
        for (item in daftarKeranjang) {
            val angkaHarga = item.produk.harga.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
            total += angkaHarga * item.jumlah
        }
        return total
    }
}