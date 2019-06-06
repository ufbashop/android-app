package br.ufba.ufbashop.objects

data class Shop (
    val name: String,
    val logoImage: String?,
    val mainSellingLocation: String,
    val score: Double,
    val contact: String,
    val starts: String,
    val ends: String,
    val campus: String,
    val ratings: List<ShopRating>,
    val products: List<Product>,
    val uuid: String = "",
    val tags: List<String>
)