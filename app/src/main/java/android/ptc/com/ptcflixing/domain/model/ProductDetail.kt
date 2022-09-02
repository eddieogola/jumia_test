package android.ptc.com.ptcflixing.domain.model

data class ProductDetail(
    val sku: String,
    val brand: String,
    val maxSavingPercentage: Int,
    val name: String,
    val price: Int,
    val specialPrice: Int,
    val rating: RatingDetail,
    val sellerDetail: SellerDetail,
    val summary: SummaryDetail
) {
    data class RatingDetail(
        val averageRating: Int,
        val ratingsTotal: Int,
    )

    data class SellerDetail(
        val sellerId: Int,
        val deliveryTime: String,
        val sellerName: String
    )

    data class SummaryDetail(
        val description: String,
        val shortDescription: String
    )
}
