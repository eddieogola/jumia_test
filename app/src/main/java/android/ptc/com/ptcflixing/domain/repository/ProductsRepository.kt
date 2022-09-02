package android.ptc.com.ptcflixing.domain.repository

import android.ptc.com.ptcflixing.core.NetworkLoading
import android.ptc.com.ptcflixing.data.local.room.entity.ProductDetailWithImages
import android.ptc.com.ptcflixing.data.local.room.entity.ProductEntity
import android.ptc.com.ptcflixing.domain.model.Product
import android.ptc.com.ptcflixing.domain.model.ProductDetail
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface ProductsRepository {
    fun getAllProducts(searchQuery: String): Flow<PagingData<ProductEntity>>

    fun getProductDetail(productId: String): Flow<NetworkLoading<ProductDetailWithImages>>

}