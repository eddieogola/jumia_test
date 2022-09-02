package android.ptc.com.ptcflixing.data.local.room.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "product_detail_entity")
@Parcelize
data class ProductDetailEntity(
    @PrimaryKey(autoGenerate = false)
    val sku: String,
    val brand: String,
    val maxSavingPercentage: Int,
    val name: String,
    val price: Int,
    val specialPrice: Int,
    @Embedded
    val rating: RatingDetailEntity,
    @Embedded
    val sellerDetail: SellerDetailEntity,
    @Embedded
    val summary: SummaryDetailEntity
) : Parcelable {
    @Parcelize
    data class RatingDetailEntity(
        val averageRating: Int,
        val ratingsTotal: Int,
    ) : Parcelable

    @Parcelize
    data class SellerDetailEntity(
        val sellerId: Int,
        val deliveryTime: String,
        val sellerName: String
    ) : Parcelable

    @Parcelize
    data class SummaryDetailEntity(
        val description: String,
        val shortDescription: String
    ) : Parcelable
}




