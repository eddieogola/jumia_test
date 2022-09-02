package android.ptc.com.ptcflixing.ui.result_list

import android.ptc.com.ptcflixing.data.remote.FakeApi
import android.ptc.com.ptcflixing.data.repository.FakeProductsRepo
import android.ptc.com.ptcflixing.data.repository.FakeUserPreferencesRepo
import android.ptc.com.ptcflixing.domain.use_case.GetConfigurations
import android.ptc.com.ptcflixing.domain.use_case.GetProducts
import android.ptc.com.ptcflixing.utils.MainDispatcherRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asFlow
import androidx.paging.PagingData
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ResultListViewModelTest {

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var resultListViewModel: ResultListViewModel
    private lateinit var getProducts: GetProducts
    private lateinit var getConfigurations: GetConfigurations
    private lateinit var fakeProductsRepo: FakeProductsRepo
    private lateinit var fakeUserPreferencesRepo: FakeUserPreferencesRepo
    private lateinit var api: FakeApi

    companion object {
        const val SEARCH_QUERY = "phone"
        const val EMPTY_SEARCH_MESSAGE = "Search something"
        const val EMPTY_QUERY = ""
        const val PRODUCT_ID = "1"
    }

    @Before
    fun setUp() {
        api = FakeApi()
        fakeUserPreferencesRepo = FakeUserPreferencesRepo(api)
        fakeProductsRepo = FakeProductsRepo()
        getProducts = GetProducts(fakeProductsRepo)
        getConfigurations = GetConfigurations(fakeUserPreferencesRepo)
        resultListViewModel = ResultListViewModel(getProducts)
    }

    @Test
    fun `SwitchUIState showNothingFound is true if no items in adapter`() = runTest {
        resultListViewModel.onEvent(ResultListViewModel.ResultUIEvent.NothingFound)

        val showNothingFound = resultListViewModel.switchUIState.asFlow().first().showNothingFound
        Truth.assertThat(showNothingFound).isTrue()
    }

    @Test
    fun `SwitchUIState showNotConnected is true if no connection`() = runTest {
        resultListViewModel.onEvent(ResultListViewModel.ResultUIEvent.Connection)

        val showNotConnected = resultListViewModel.switchUIState.asFlow().first().showNotConnected
        Truth.assertThat(showNotConnected).isTrue()
    }

    @Test
    fun `SwitchUIState showProducts is true if products are available`() = runTest {
        resultListViewModel.onEvent(ResultListViewModel.ResultUIEvent.ShowProducts)

        val showProducts = resultListViewModel.switchUIState.asFlow().first().showProducts
        Truth.assertThat(showProducts).isTrue()
    }

    @Test
    fun `SwitchUIState showLoading is true if products are loading`() = runTest {
        resultListViewModel.onEvent(ResultListViewModel.ResultUIEvent.Loading)

        val showLoading = resultListViewModel.switchUIState.asFlow().first().showLoading
        Truth.assertThat(showLoading).isTrue()
    }

    @Test
    fun `searchJumia() gets Products`() = runBlocking {
        val pagingData = getProducts(SEARCH_QUERY).first()
        Truth.assertThat(pagingData).isNotNull()
        Truth.assertThat(pagingData).isInstanceOf(PagingData::class.java)
    }

    @Test
    fun `Empty query returns EMPTY_SEARCH_MESSAGE`() = runTest {
        resultListViewModel.onEvent(ResultListViewModel.ResultUIEvent.SearchSubmit(EMPTY_QUERY))
        val channel = resultListViewModel.channel.first()

        when (channel) {
            is ResultListViewModel.ResultListChannel.Message -> {
                Truth.assertThat(channel.message).contains(EMPTY_SEARCH_MESSAGE)
            }
            is ResultListViewModel.ResultListChannel.Navigate -> {}
        }
    }

    @Test
    fun `correct id is later passed to navigation`() = runTest{
        resultListViewModel.onEvent(ResultListViewModel.ResultUIEvent.ProductClicked(PRODUCT_ID))
        val channel = resultListViewModel.channel.first()

        when (channel) {
            is ResultListViewModel.ResultListChannel.Message -> {}
            is ResultListViewModel.ResultListChannel.Navigate -> {
                Truth.assertThat(channel.navArg).contains(PRODUCT_ID)
                Truth.assertThat(Integer.parseInt(channel.navArg)).isEqualTo(1)
            }
        }
    }
}