package br.ufba.ufbashop.objects

import android.os.Parcel
import android.os.Parcelable

data class Shop (
    val name: String = "",
    val logoImage: String? = null,
    val mainSellingLocation: String = "",
    val starts: String = "00:00",
    val ends: String = "00:00",
    val campus: String = "",
    val ratings: List<ShopRating> = emptyList(),
    val products: List<Product> = emptyList(),
    val phoneNumber: String? = null,
    val shopDescription: String = "",
    val userId: String = "",
    val shopId: String = ""
)