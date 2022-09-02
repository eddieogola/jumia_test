package android.ptc.com.ptcflixing.data.local.room.dao

import android.ptc.com.ptcflixing.data.local.room.entity.ImageEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ProductDetailImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageEntity: ImageEntity)
}