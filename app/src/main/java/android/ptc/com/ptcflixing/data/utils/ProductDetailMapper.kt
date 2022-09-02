package android.ptc.com.ptcflixing.data.utils

import android.ptc.com.ptcflixing.core.EntityMapper
import android.ptc.com.ptcflixing.data.local.room.entity.ProductDetailEntity
import android.ptc.com.ptcflixing.data.local.room.entity.ProductDetailEntity.*
import android.ptc.com.ptcflixing.data.utils.RatingDetailMapper.Companion.mapToDomainModel
import android.ptc.com.ptcflixing.data.utils.RatingDetailMapper.Companion.mapToEntity
import android.ptc.com.ptcflixing.data.utils.SellerDetailMapper.Companion.mapToDomainModel
import android.ptc.com.ptcflixing.data.utils.SellerDetailMapper.Companion.mapToEntity
import android.ptc.com.ptcflixing.data.utils.SummaryDetailMapper.Companion.mapToDomainModel
import android.ptc.com.ptcflixing.data.utils.SummaryDetailMapper.Companion.mapToEntity
import android.ptc.com.ptcflixing.domain.model.ProductDetail
import android.ptc.com.ptcflixing.domain.model.ProductDetail.*

class ProductDetailMapper {
    companion object : EntityMapper<ProductDetailEntity, ProductDetail> {
        override fun ProductDetailEntity.mapToDomainModel(): ProductDetail {
            return ProductDetail(
                brand = brand,
                sku = sku,
                maxSavingPercentage = maxSavingPercentage,
                name = name,
                price = price,
                specialPrice = specialPrice,
                rating = rating.mapToDomainModel(),
                sellerDetail = sellerDetail.mapToDomainModel(),
                summary = summary.mapToDomainModel()
            )
        }

        override fun ProductDetail.mapToEntity(): ProductDetailEntity {
            return ProductDetailEntity(
                brand = brand,
                sku = sku,
                maxSavingPercentage = maxSavingPercentage,
                name = name,
                price = price,
                specialPrice = specialPrice,
                rating = rating.mapToEntity(),
                sellerDetail = sellerDetail.mapToEntity(),
                summary = summary.mapToEntity()
            )
        }
    }


}

class RatingDetailMapper {
    companion object : EntityMapper<RatingDetailEntity, RatingDetail> {
        override fun RatingDetailEntity.mapToDomainModel(): RatingDetail {
            return RatingDetail(
                averageRating = averageRating,
                ratingsTotal = ratingsTotal
            )
        }

        override fun RatingDetail.mapToEntity(): RatingDetailEntity {
            return RatingDetailEntity(
                averageRating = averageRating,
                ratingsTotal = ratingsTotal
            )
        }
    }
}

class SellerDetailMapper {
    companion object : EntityMapper<SellerDetailEntity, SellerDetail> {
        override fun SellerDetailEntity.mapToDomainModel(): SellerDetail {
            return SellerDetail(
                sellerId = sellerId,
                deliveryTime = deliveryTime,
                sellerName = sellerName
            )
        }

        override fun SellerDetail.mapToEntity(): SellerDetailEntity {
            return SellerDetailEntity(
                sellerId = sellerId,
                deliveryTime = deliveryTime,
                sellerName = sellerName
            )
        }
    }
}

class SummaryDetailMapper {
    companion object : EntityMapper<SummaryDetailEntity, SummaryDetail> {
        override fun SummaryDetailEntity.mapToDomainModel(): SummaryDetail {
            return SummaryDetail(
                description = description,
                shortDescription = shortDescription
            )
        }

        override fun SummaryDetail.mapToEntity(): SummaryDetailEntity {
            return SummaryDetailEntity(
                description = description,
                shortDescription = shortDescription
            )
        }
    }
}