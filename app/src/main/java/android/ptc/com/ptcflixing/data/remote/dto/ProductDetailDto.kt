package android.ptc.com.ptcflixing.data.remote.dto

import android.ptc.com.ptcflixing.data.local.room.entity.ImageEntity
import android.ptc.com.ptcflixing.data.local.room.entity.ProductDetailEntity

data class ProductDetailDto(
    val metadata: Metadata,
    val success: Boolean
) {
    data class Metadata(
        val brand: String,
        val image_list: List<String>,
        val max_saving_percentage: Int,
        val name: String,
        val price: Int,
        val rating: Rating,
        val seller_entity: SellerEntity,
        val sku: String,
        val special_price: Int,
        val summary: Summary
    ) {

        fun toProductDetailEntity(): ProductDetailEntity {
            return ProductDetailEntity(
                sku = sku,
                brand = brand,
                maxSavingPercentage = max_saving_percentage,
                name = name,
                price = price,
                specialPrice = special_price,
                rating = rating.toRatingDetailEntity(),
                sellerDetail = seller_entity.toSellerEntity(),
                summary = summary.toSummaryEntity()
            )

        }

    }

    data class Rating(
        val average: Int,
        val ratings_total: Int
    ) {
        fun toRatingDetailEntity(): ProductDetailEntity.RatingDetailEntity {
            return ProductDetailEntity.RatingDetailEntity(
                averageRating = average,
                ratingsTotal = ratings_total

            )
        }
    }

    data class SellerEntity(
        val delivery_time: String,
        val id: Int,
        val name: String
    ) {
        fun toSellerEntity(): ProductDetailEntity.SellerDetailEntity {
            return ProductDetailEntity.SellerDetailEntity(
                sellerId = id,
                deliveryTime = delivery_time,
                sellerName = name
            )
        }
    }

    data class Summary(
        val description: String,
        val short_description: String
    ) {
        fun toSummaryEntity(): ProductDetailEntity.SummaryDetailEntity {
            return ProductDetailEntity.SummaryDetailEntity(
                description = description,
                shortDescription = short_description
            )
        }
    }

}

