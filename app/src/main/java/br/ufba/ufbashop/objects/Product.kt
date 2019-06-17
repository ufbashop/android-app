package br.ufba.ufbashop.objects

import android.os.Parcel
import android.os.Parcelable


data class Product (
    val name: String = "",
    val description: String = "",
    val shop: String = "",
    val price: Double = 0.0,
    val discount: Double = 0.0,
    val mainImage: String? = null,
    val images: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val ratings: List<ShopRating> = emptyList(),
    val questions: List<Comment> = emptyList(),
    val used: Boolean = false,
    val category: Int = 0,
    var sold: Int = 0,
    var available: Boolean = false
)