package android.ptc.com.ptcflixing.data.local.room.dao

import android.ptc.com.ptcflixing.data.local.room.entity.ProductDetailEntity
import android.ptc.com.ptcflixing.data.local.room.entity.ProductDetailWithImages
import android.ptc.com.ptcflixing.data.local.room.entity.ProductEntity
import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface ProductDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductDetail(productDetail: ProductDetailEntity)

    @Transaction
    @Query("SELECT * FROM product_detail_entity WHERE sku= :productId")
    suspend fun getProductDetailWithImages(productId: String): ProductDetailWithImages

}