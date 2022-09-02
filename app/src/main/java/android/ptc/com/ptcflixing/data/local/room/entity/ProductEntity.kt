package android.ptc.com.ptcflixing.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_entity")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    val sku: String,
    val brand: String,
    val image: String,
    val maxSavingPercentage: Int,
    val name: String,
    val price: Int,
    val ratingAverage: Int,
    val specialPrice: Int
)
