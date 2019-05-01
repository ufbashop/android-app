package br.ufba.ufbashop.objects


data class Procuct (
    val name: String,
    val description: String,
    val price: Double,
    val discount: Double = 0.0,
    val mainImage: String, // image link
    val images: Array<String>,
    val seller: Seller,
    val details: String,
    val score: Double,
    val ratings: Array<ShopRating>,
    val questions: Array<Comment>,
    val used: Boolean? = null , // Null if it's food
    val category: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Procuct

        if (name != other.name) return false
        if (description != other.description) return false
        if (price != other.price) return false
        if (discount != other.discount) return false
        if (mainImage != other.mainImage) return false
        if (!images.contentEquals(other.images)) return false
        if (seller != other.seller) return false
        if (details != other.details) return false
        if (score != other.score) return false
        if (!ratings.contentEquals(other.ratings)) return false
        if (!questions.contentEquals(other.questions)) return false
        if (used != other.used) return false
        if (category != other.category) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + discount.hashCode()
        result = 31 * result + mainImage.hashCode()
        result = 31 * result + images.contentHashCode()
        result = 31 * result + seller.hashCode()
        result = 31 * result + details.hashCode()
        result = 31 * result + score.hashCode()
        result = 31 * result + ratings.contentHashCode()
        result = 31 * result + questions.contentHashCode()
        result = 31 * result + (used?.hashCode() ?: 0)
        result = 31 * result + category
        return result
    }
}
