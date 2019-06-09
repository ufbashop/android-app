package br.ufba.ufbashop.objects

data class Shop (
    val name: String,
    val logoImage: String?,
    val mainSellingLocation: String,
    val starts: String,
    val ends: String,
    val campus: String,
    val ratings: List<ShopRating>,
    val products: List<Product>,
    val phoneNumber: String?,
    val shopDescription: String
)