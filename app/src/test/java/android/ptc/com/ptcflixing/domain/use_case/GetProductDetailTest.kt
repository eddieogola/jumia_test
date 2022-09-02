package android.ptc.com.ptcflixing.domain.use_case

import android.ptc.com.ptcflixing.core.NetworkLoading
import android.ptc.com.ptcflixing.data.local.room.FakeDb.Companion.productDetailsDb
import android.ptc.com.ptcflixing.data.repository.FakeProductsRepo
import android.ptc.com.ptcflixing.ui.item_detail.ItemDetailViewModel
import android.ptc.com.ptcflixing.ui.item_detail.ItemDetailViewModelTest
import android.ptc.com.ptcflixing.utils.MainDispatcherRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class GetProductDetailTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var getProductDetail: GetProductDetail
    private lateinit var fakeProductsRepo: FakeProductsRepo
    private lateinit var viewModel: ItemDetailViewModel

    companion object {
        const val PRODUCT_ID_1 = "1"
        const val PRODUCT_ID_2 = "2"
        const val PRODUCT_ID_NOT_EXIST = "3"
        const val NO_PRODUCT = "No Product"
    }

    @Before
    fun setUp() {
        fakeProductsRepo = FakeProductsRepo()
        getProductDetail = GetProductDetail(fakeProductsRepo)
    }

    @Test
    fun getProductDetailByIdReturnCorrectProduct() = runBlocking {
        val productDetail = getProductDetail(PRODUCT_ID_1).first().data
        assertThat(productDetail).isEqualTo(productDetailsDb[0])
    }

    @Test
    fun `Error message is correct`() = runTest {
        val networkLoading = getProductDetail(ItemDetailViewModelTest.PRODUCT_ID_NOT_EXIST).first()

        when (networkLoading) {
            is NetworkLoading.Error -> {
                assertThat(networkLoading.message).isEqualTo(ItemDetailViewModelTest.NO_PRODUCT)
            }
            is NetworkLoading.Loading -> {}
            is NetworkLoading.Success -> {}
        }
    }

    @Test
    fun `Getting the right product`() = runTest {
        val networkLoading = getProductDetail(ItemDetailViewModelTest.PRODUCT_ID_2).first()

        when (networkLoading) {
            is NetworkLoading.Error -> {}
            is NetworkLoading.Loading -> {}
            is NetworkLoading.Success -> {
                assertThat(networkLoading.data?.productDetailEntity?.sku).isEqualTo(
                    ItemDetailViewModelTest.PRODUCT_ID_2
                )
                assertThat(networkLoading.data?.productDetailEntity?.sku).isNotEqualTo(
                    ItemDetailViewModelTest.PRODUCT_ID_1
                )
            }
        }
    }
}