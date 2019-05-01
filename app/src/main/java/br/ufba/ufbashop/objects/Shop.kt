package br.ufba.ufbashop.objects

data class Shop (
    val mainSellingLocation: String,
    val score: Double,
    val ratings: Array<ShopRating>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Shop

        if (mainSellingLocation != other.mainSellingLocation) return false
        if (score != other.score) return false
        if (!ratings.contentEquals(other.ratings)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mainSellingLocation.hashCode()
        result = 31 * result + score.hashCode()
        result = 31 * result + ratings.contentHashCode()
        return result
    }
}
