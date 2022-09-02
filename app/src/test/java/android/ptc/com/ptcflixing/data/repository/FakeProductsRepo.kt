package android.ptc.com.ptcflixing.data.repository

import android.ptc.com.ptcflixing.core.NetworkLoading
import android.ptc.com.ptcflixing.data.local.room.FakeDb.Companion.productDetailsDb
import android.ptc.com.ptcflixing.data.local.room.entity.ProductDetailWithImages
import android.ptc.com.ptcflixing.data.local.room.entity.ProductEntity
import android.ptc.com.ptcflixing.domain.repository.ProductsRepository
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeProductsRepo : ProductsRepository {

    companion object{
        const val NO_PRODUCT = "No Product"
    }

    override fun getAllProducts(searchQuery: String): Flow<PagingData<ProductEntity>> {
        return flow {
            emit(PagingData.empty())
        }
    }

    override fun getProductDetail(productId: String): Flow<NetworkLoading<ProductDetailWithImages>> {
        return flow {
            val productDetail = productDetailsDb.find { it.productDetailEntity.sku == productId }
            productDetail?.let {
                emit(NetworkLoading.Success(productDetail))
                emit(NetworkLoading.Loading(productDetail))
            } ?: emit(NetworkLoading.Error(null, NO_PRODUCT ))
        }
    }
}