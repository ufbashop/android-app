package br.ufba.ufbashop.objects

data class Shop (
    val name: String,
    val logo: String?,
    val mainSellingLocation: String,
    val score: Double,
    val ratings: List<ShopRating>,
    val products: List<Product>,
    var uuid: String = ""
)