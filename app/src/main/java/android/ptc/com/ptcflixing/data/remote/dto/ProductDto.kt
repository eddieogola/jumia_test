package android.ptc.com.ptcflixing.data.remote.dto

import android.ptc.com.ptcflixing.data.local.room.entity.ProductEntity

data class ProductDto(
    val metadata: Metadata,
    val success: Boolean
){
    data class Metadata(
        val results: List<Result>,
        val sort: String,
        val title: String,
        val total_products: Int
    )

    data class Result(
        val brand: String,
        val image: String,
        val max_saving_percentage: Int,
        val name: String,
        val price: Int,
        val rating_average: Int,
        val sku: String,
        val special_price: Int
    ){
        fun toEntity(): ProductEntity {
            return ProductEntity(
                brand = brand,
                image = image,
                sku = sku,
                maxSavingPercentage = max_saving_percentage,
                name = name,
                price = price,
                ratingAverage = rating_average,
                specialPrice = special_price
            )
        }
    }
}