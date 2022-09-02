package android.ptc.com.ptcflixing.data.paging

import android.ptc.com.ptcflixing.core.Constants.Companion.STARTING_PAGE_INDEX
import android.ptc.com.ptcflixing.data.local.room.JumiaTestDatabase
import android.ptc.com.ptcflixing.data.local.room.entity.ProductEntity
import android.ptc.com.ptcflixing.data.local.room.entity.ProductRemoteKey
import android.ptc.com.ptcflixing.data.remote.JumiaTestApi
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class ProductRemoteMediator(
    private val database: JumiaTestDatabase,
    private val api: JumiaTestApi,
    private val searchQuery: String
) : RemoteMediator<Int, ProductEntity>() {
    private val productEntityDao = database.productEntityDao()
    private val productRemoteKeyDao = database.productRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
    ): MediatorResult {

        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextKey
                }
            }

            val response = api.getResultList(query = searchQuery, pageNo = currentPage)
            val products = response.body()?.metadata?.results ?: emptyList()

            val endOfPaginationReached = !response.isSuccessful
            val prevKey = if (currentPage == STARTING_PAGE_INDEX) null else currentPage - 1
            val nextKey = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    productEntityDao.deleteAllProducts()
                    productRemoteKeyDao.deleteAllProductRemoteKeys()
                }

                val keys = products.map { product ->
                    ProductRemoteKey(
                        id = product.sku,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                productRemoteKeyDao.insertAllProductRemoteKeys(productRemoteKeys = keys)
                productEntityDao.insertAllProducts(products = products.map { it.toEntity() })
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ProductEntity>
    ): ProductRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.sku?.let { id ->
                productRemoteKeyDao.getProductRemoteKey(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, ProductEntity>
    ): ProductRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { product ->
                productRemoteKeyDao.getProductRemoteKey(id = product.sku)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, ProductEntity>
    ): ProductRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { product ->
                productRemoteKeyDao.getProductRemoteKey(id = product.sku)
            }
    }

}
