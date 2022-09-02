package android.ptc.com.ptcflixing.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageEntity(
    val productDetailId: String ,
    @PrimaryKey(autoGenerate = false)
    val imageUrl: String
)
