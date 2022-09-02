package android.ptc.com.ptcflixing.data.utils

import android.ptc.com.ptcflixing.core.EntityMapper
import android.ptc.com.ptcflixing.data.local.room.entity.ProductEntity
import android.ptc.com.ptcflixing.domain.model.Product

class ProductMapper {
    companion object :
        EntityMapper<ProductEntity, Product> {
        override fun ProductEntity.mapToDomainModel(): Product {
            return Product(
                sku = sku,
                brand = brand,
                image = image,
                maxSavingPercentage = maxSavingPercentage,
                name = name,
                price = price,
                ratingAverage = ratingAverage,
                specialPrice = specialPrice
            )
        }

        override fun Product.mapToEntity(): ProductEntity {
            return ProductEntity(
                sku = sku,
                brand = brand,
                image = image,
                maxSavingPercentage = maxSavingPercentage,
                name = name,
                price = price,
                ratingAverage = ratingAverage,
                specialPrice = specialPrice
            )
        }
    }
}
