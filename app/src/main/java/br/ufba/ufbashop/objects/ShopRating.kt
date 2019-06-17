package br.ufba.ufbashop.objects

data class Comment (
    val comment : String = "",
    val timestamp: Long = 0
)

data class ShopRating (
    val score: Double,
    val comment: Comment
)
