package android.ptc.com.ptcflixing.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val sku: String,
    val brand: String,
    val image: String,
    val maxSavingPercentage: Int,
    val name: String,
    val price: Int,
    val ratingAverage: Int,
    val specialPrice: Int
) : Parcelable