package br.ufba.ufbashop.objects

data class User (
    val uuid: String = "",
    val userImage: String? = null,
    val name: String = "",
    val desireList: List<String> = emptyList(),
    val favoritesShops: List<String> = emptyList()
)