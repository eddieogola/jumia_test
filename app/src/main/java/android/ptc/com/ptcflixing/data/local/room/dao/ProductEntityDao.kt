package android.ptc.com.ptcflixing.data.local.room.dao

import android.ptc.com.ptcflixing.data.local.room.entity.ProductEntity
import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface ProductEntityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM product_entity")
    fun getProducts(): PagingSource<Int, ProductEntity>

    @Query("DELETE FROM product_entity")
    suspend fun deleteAllProducts()

}