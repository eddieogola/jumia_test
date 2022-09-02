package android.ptc.com.ptcflixing.domain.use_case

import android.ptc.com.ptcflixing.data.repository.FakeProductsRepo
import android.ptc.com.ptcflixing.data.utils.ProductMapper.Companion.mapToDomainModel
import android.ptc.com.ptcflixing.domain.model.Product
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FakeGetProducts(private val productsRepo: FakeProductsRepo) {
    operator fun invoke(searchQuery: String): Flow<PagingData<Product>> = productsRepo.getAllProducts(searchQuery).map { pagingData ->
        pagingData.map { productEntity ->
            productEntity.mapToDomainModel()
        }
    }
}