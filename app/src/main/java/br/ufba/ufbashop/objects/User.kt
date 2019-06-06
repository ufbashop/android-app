package br.ufba.ufbashop.objects

data class User (
    val uuid: String,
    val userImage: String?,
    val name: String,
    val desireList: List<String>,
    val favoritesShops: List<String>
)