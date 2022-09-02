package android.ptc.com.ptcflixing.ui.search

import android.ptc.com.ptcflixing.utils.LiveDataTestUtils.Companion.getOrAwaitValue
import android.ptc.com.ptcflixing.utils.MainDispatcherRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchViewModelTest {
    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: SearchViewModel
    private lateinit var searchSubmit: SearchViewModel.SearchEvent.SearchSubmit
    private lateinit var searchMessage: SearchViewModel.SearchChannel.Message


    companion object {
        const val SEARCH_QUERY = "Hello"
        const val SEARCH_MESSAGE = "Wassup"
    }


    @Before
    fun setUp() {
        viewModel = SearchViewModel()
        searchSubmit = SearchViewModel.SearchEvent.SearchSubmit("")
        searchMessage = SearchViewModel.SearchChannel.Message("")
    }

    @Test
    fun correctSearchQueryIsSentToChannel() = runTest {
        viewModel.onEvent(SearchViewModel.SearchEvent.SearchSubmit(SEARCH_QUERY))


        val channel = viewModel.searchChannel.first()

                when(channel){
                    is SearchViewModel.SearchChannel.Message -> {}
                    is SearchViewModel.SearchChannel.Navigate -> {
                       Truth.assertThat(channel.searchQuery).contains(SEARCH_QUERY)
                    }
                }

    }

    @Test
    fun correctMessageIsSentToChannel() = runTest {
        viewModel.onEvent(SearchViewModel.SearchEvent.SearchSubmit(SEARCH_MESSAGE))


        val channel = viewModel.searchChannel.first()

        when(channel){
            is SearchViewModel.SearchChannel.Message -> {
                Truth.assertThat(channel.message).contains(SEARCH_MESSAGE)
            }
            is SearchViewModel.SearchChannel.Navigate -> {}
        }

    }
}