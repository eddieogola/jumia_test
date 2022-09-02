package android.ptc.com.ptcflixing.ui.item_detail

import android.ptc.com.ptcflixing.data.repository.FakeProductsRepo
import android.ptc.com.ptcflixing.domain.use_case.GetProductDetail
import android.ptc.com.ptcflixing.utils.MainDispatcherRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ItemDetailViewModelTest {

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
        viewModel = ItemDetailViewModel(getProductDetail)

    }

    @Test
    fun `correct product detail is sent to the state`() = runTest {
        viewModel.getProduct(PRODUCT_ID_2).join()


        val productDetailFound = viewModel.state.asFlow().first().productDetail.productDetailEntity

        assertThat(productDetailFound.sku).isEqualTo(PRODUCT_ID_2)

    }


}