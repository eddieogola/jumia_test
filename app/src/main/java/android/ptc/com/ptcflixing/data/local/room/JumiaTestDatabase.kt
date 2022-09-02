package android.ptc.com.ptcflixing.data.local.room

import android.ptc.com.ptcflixing.data.local.room.dao.ProductDetailDao
import android.ptc.com.ptcflixing.data.local.room.dao.ProductDetailImageDao
import android.ptc.com.ptcflixing.data.local.room.dao.ProductEntityDao
import android.ptc.com.ptcflixing.data.local.room.dao.ProductRemoteKeyDao
import android.ptc.com.ptcflixing.data.local.room.entity.ImageEntity
import android.ptc.com.ptcflixing.data.local.room.entity.ProductDetailEntity
import android.ptc.com.ptcflixing.data.local.room.entity.ProductEntity
import android.ptc.com.ptcflixing.data.local.room.entity.ProductRemoteKey
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ProductEntity::class,
        ProductDetailEntity::class,
        ProductRemoteKey::class,
        ImageEntity::class
    ],
    version = 1,
    exportSchema = true,
)

abstract class JumiaTestDatabase : RoomDatabase() {

    abstract fun productEntityDao(): ProductEntityDao
    abstract fun productRemoteKeyDao(): ProductRemoteKeyDao
    abstract fun productDetailDao(): ProductDetailDao
    abstract fun productDetailImageDao(): ProductDetailImageDao
}