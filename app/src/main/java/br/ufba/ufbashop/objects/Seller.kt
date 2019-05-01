package br.ufba.ufbashop.objects

data class Seller (
    val name: String,
    val shops: Array<Shop>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Seller

        if (name != other.name) return false
        if (!shops.contentEquals(other.shops)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + shops.contentHashCode()
        return result
    }
}


