package android.ptc.com.ptcflixing.data.local.room

import android.ptc.com.ptcflixing.data.local.room.entity.ImageEntity
import android.ptc.com.ptcflixing.data.local.room.entity.ProductDetailEntity
import android.ptc.com.ptcflixing.data.local.room.entity.ProductDetailWithImages
import android.ptc.com.ptcflixing.data.local.room.entity.ProductEntity

class FakeDb {

    companion object {
        private val productEntity1 = ProductEntity(
            sku = "1",
            brand = "Samsung",
            image = "image1",
            maxSavingPercentage = 25,
            name = "name1",
            price = 30000,
            ratingAverage = 4,
            specialPrice = 25000
        )

        private val productEntity2 = ProductEntity(
            sku = "2",
            brand = "Xiaomi",
            image = "image2",
            maxSavingPercentage = 10,
            name = "name1",
            price = 30000,
            ratingAverage = 4,
            specialPrice = 24000
        )

        private val ratingDetailEntity1 = ProductDetailEntity.RatingDetailEntity(5, 20)
        private val ratingDetailEntity2 = ProductDetailEntity.RatingDetailEntity(4, 16)

        private val sellerDetailEntity1 =
            ProductDetailEntity.SellerDetailEntity(1, "1450hrs", "Brian")
        private val sellerDetailEntity2 =
            ProductDetailEntity.SellerDetailEntity(1, "1620hrs", "John")

        private val summary1 = ProductDetailEntity.SummaryDetailEntity("good one good", "good")
        private val summary2 = ProductDetailEntity.SummaryDetailEntity("maybe maybe", "meh")

        private val productDetailEntity1 = ProductDetailEntity(
            sku = "1",
            brand = "Samsung",
            maxSavingPercentage = 20,
            name = "New",
            price = 10000,
            specialPrice = 8500,
            rating = ratingDetailEntity1,
            sellerDetail = sellerDetailEntity1,
            summary = summary1

        )

        private val productDetailEntity2 = ProductDetailEntity(
            sku = "2",
            brand = "Oppo",
            maxSavingPercentage = 12,
            name = "Tri",
            price = 8000,
            specialPrice = 7500,
            rating = ratingDetailEntity2,
            sellerDetail = sellerDetailEntity2,
            summary = summary2
        )

        private val images1 = mutableListOf<ImageEntity>()
        private val images2 = mutableListOf<ImageEntity>()

        private val productDetail1 = ProductDetailWithImages(productDetailEntity1, images1)
        private val productDetail2 = ProductDetailWithImages(productDetailEntity2, images2)


        val productDetailsDb = listOf(productDetail1, productDetail2)
        val productDb = listOf(productEntity1, productEntity2)
    }

}