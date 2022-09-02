package android.ptc.com.ptcflixing.domain.use_case

import android.ptc.com.ptcflixing.core.NetworkLoading
import android.ptc.com.ptcflixing.data.local.room.entity.ProductDetailWithImages
import android.ptc.com.ptcflixing.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductDetail @Inject constructor(
    private val productsRepositoryRepo: ProductsRepository
) {
    operator fun invoke(productId: String): Flow<NetworkLoading<ProductDetailWithImages>> =
        productsRepositoryRepo.getProductDetail(productId)
}