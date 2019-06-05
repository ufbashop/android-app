package br.ufba.ufbashop.objects


data class Product (
    val name: String,
    val description: String,
    val shop: Shop,
    val price: Double,
    val discount: Double = 0.0,
    val mainImage: String, // image link
    val images: List<String>,
    val details: String,
    val score: Double,
    val ratings: List<ShopRating>,
    val questions: List<Comment>,
    val used: Boolean? = null , // Null if it's food
    val category: Int,
    var sold: Int,
    var available: Boolean
)