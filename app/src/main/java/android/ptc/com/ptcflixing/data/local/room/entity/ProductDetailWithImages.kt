package android.ptc.com.ptcflixing.data.local.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ProductDetailWithImages(
    @Embedded val productDetailEntity: ProductDetailEntity,
    @Relation(
        parentColumn = "sku",
        entityColumn = "productDetailId"
    )
    val images: List<ImageEntity>
)