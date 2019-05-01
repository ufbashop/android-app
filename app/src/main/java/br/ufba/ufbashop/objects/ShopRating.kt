package br.ufba.ufbashop.objects

import java.util.Date

data class Comment (
    val comment : String = "",
    val date: Date
)

data class ShopRating (
    val score: Double,
    val comment: Comment
)
