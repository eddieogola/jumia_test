package android.ptc.com.ptcflixing.ui.utils

import android.ptc.com.ptcflixing.data.local.room.entity.ImageEntity
import android.ptc.com.ptcflixing.domain.model.Product

interface OnItemClickListener {
    fun onProductClick(product: Product){}
    fun onImageClick(image: ImageEntity){}
}