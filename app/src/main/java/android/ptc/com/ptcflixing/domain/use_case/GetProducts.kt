package android.ptc.com.ptcflixing.domain.use_case

import android.ptc.com.ptcflixing.data.utils.ProductMapper.Companion.mapToDomainModel
import android.ptc.com.ptcflixing.domain.model.Product
import android.ptc.com.ptcflixing.domain.repository.ProductsRepository
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProducts @Inject constructor(
    private val productsRepositoryRepo: ProductsRepository
) {
    operator fun invoke(searchQuery: String): Flow<PagingData<Product>> = productsRepositoryRepo.getAllProducts(searchQuery).map { pagingData ->
        pagingData.map { productEntity ->
            productEntity.mapToDomainModel()
        }
    }
}