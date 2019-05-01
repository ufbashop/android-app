package br.ufba.ufbashop.objects

data class Shop (
    val mainSellingLocation: String,
    val score: Double,
    val ratings: Array<ShopRating>,
    val products: Array<Procuct>,
    val sellers: Array<Seller>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Shop

        if (mainSellingLocation != other.mainSellingLocation) return false
        if (score != other.score) return false
        if (!ratings.contentEquals(other.ratings)) return false
        if (!products.contentEquals(other.products)) return false
        if (!sellers.contentEquals(other.sellers)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mainSellingLocation.hashCode()
        result = 31 * result + score.hashCode()
        result = 31 * result + ratings.contentHashCode()
        result = 31 * result + products.contentHashCode()
        result = 31 * result + sellers.contentHashCode()
        return result
    }
}
