package android.ptc.com.ptcflixing.data.repository

import android.ptc.com.ptcflixing.core.Constants.Companion.ERROR_200_MESSAGE
import android.ptc.com.ptcflixing.core.Constants.Companion.ERROR_404_MESSAGE
import android.ptc.com.ptcflixing.core.Constants.Companion.HTTP_ERROR
import android.ptc.com.ptcflixing.core.Constants.Companion.INTERNET_CONNECTION_ERROR
import android.ptc.com.ptcflixing.core.Constants.Companion.ITEMS_PER_PAGE
import android.ptc.com.ptcflixing.core.NetworkLoading
import android.ptc.com.ptcflixing.data.local.room.JumiaTestDatabase
import android.ptc.com.ptcflixing.data.local.room.entity.ImageEntity
import android.ptc.com.ptcflixing.data.local.room.entity.ProductDetailWithImages
import android.ptc.com.ptcflixing.data.local.room.entity.ProductEntity
import android.ptc.com.ptcflixing.data.paging.ProductRemoteMediator
import android.ptc.com.ptcflixing.data.remote.JumiaTestApi
import android.ptc.com.ptcflixing.domain.repository.ProductsRepository
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
@Singleton
class ProductsRepositoryImpl @Inject constructor(
    private val api: JumiaTestApi,
    private val database: JumiaTestDatabase
) : ProductsRepository {
    private val dao = database.productDetailDao()
    private val imageDao = database.productDetailImageDao()
    override fun getAllProducts(searchQuery: String): Flow<PagingData<ProductEntity>> {
        val pagingSourceFactory = { database.productEntityDao().getProducts() }

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = ProductRemoteMediator(
                database = database,
                api = api,
                searchQuery
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow

    }

    override fun getProductDetail(productId: String): Flow<NetworkLoading<ProductDetailWithImages>> =
        flow {
            val productDetail = dao.getProductDetailWithImages(productId)
            emit(NetworkLoading.Loading(productDetail))

            try {
                api.getDetailItem(productId).metadata.let { product ->
                    dao.insertProductDetail(product.toProductDetailEntity())
                    product.image_list.forEach { url ->
                        imageDao.insertImage(ImageEntity(productDetailId = product.sku,imageUrl = url))
                    }
                    emit(NetworkLoading.Success(productDetail))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(NetworkLoading.Error(productDetail, INTERNET_CONNECTION_ERROR))
            } catch (e: HttpException) {
                e.printStackTrace()
                println(e.code())
                emit(NetworkLoading.Error(productDetail, HTTP_ERROR))
            } catch (e: NullPointerException) {
                e.printStackTrace()
                emit(NetworkLoading.Error(null, ERROR_200_MESSAGE))
            } catch (e: Throwable) {
                emit(NetworkLoading.Error(null, ERROR_404_MESSAGE))
            }


        }
}