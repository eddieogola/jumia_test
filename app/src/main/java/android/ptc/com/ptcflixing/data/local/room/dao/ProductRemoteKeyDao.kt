package android.ptc.com.ptcflixing.data.local.room.dao

import android.ptc.com.ptcflixing.data.local.room.entity.ProductRemoteKey
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProductRemoteKeys(productRemoteKeys: List<ProductRemoteKey>)

    @Query("SELECT * FROM product_remote_key WHERE id = :id")
    suspend fun getProductRemoteKey(id: String): ProductRemoteKey?

    @Query("DELETE FROM product_remote_key")
    suspend fun deleteAllProductRemoteKeys()
}