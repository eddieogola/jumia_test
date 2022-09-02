package android.ptc.com.ptcflixing.ui.result_list

import android.ptc.com.ptcflixing.core.Constants.Companion.EMPTY_SEARCH_MESSAGE
import android.ptc.com.ptcflixing.domain.model.CurrencyConfig
import android.ptc.com.ptcflixing.domain.model.Product
import android.ptc.com.ptcflixing.domain.use_case.GetConfigurations
import android.ptc.com.ptcflixing.domain.use_case.GetProducts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultListViewModel @Inject constructor(
    private val getProducts: GetProducts,
    ) : ViewModel() {

    private val _currencyConfigDataStore = MutableStateFlow(CurrencyConfig())
    val currencyConfig = _currencyConfigDataStore.asStateFlow()


    private val _channel = Channel<ResultListChannel>()
    val channel = _channel.receiveAsFlow()

    private val _state = MutableLiveData<ResultsUIState>()
    val state: LiveData<ResultsUIState> = _state

    private val _switchUIState = MutableLiveData<SwitchUIState>()
    val switchUIState: LiveData<SwitchUIState> = _switchUIState


    private fun searchJumia(search: String) = viewModelScope.launch {
        getProducts(search).cachedIn(viewModelScope).collect {
            _state.value = ResultsUIState(it)
        }
    }

    private fun onClick(productId: String) = viewModelScope.launch {
        _channel.send(ResultListChannel.Navigate(productId))
    }

    private fun sendMessage(message: String) = viewModelScope.launch {
        _channel.send(ResultListChannel.Message(message))
    }

    fun onEvent(event: ResultUIEvent) {
        when (event) {
            is ResultUIEvent.SearchSubmit -> {
                if (event.search.isBlank()) sendMessage(EMPTY_SEARCH_MESSAGE)
                else searchJumia(event.search)
            }
            is ResultUIEvent.ProductClicked -> {
                onClick(event.productId)

            }
            is ResultUIEvent.Connection -> {
                _switchUIState.value = SwitchUIState(
                    showNotConnected = true,
                    showNothingFound = false,
                    showProducts = false,
                    showLoading = false

                )
            }
            is ResultUIEvent.NothingFound -> {
                _switchUIState.value = SwitchUIState(
                    showNotConnected = false,
                    showNothingFound = true,
                    showProducts = false,
                    showLoading = false
                )
            }
            is ResultUIEvent.ShowProducts -> {
                _switchUIState.value = SwitchUIState(
                    showNotConnected = false,
                    showNothingFound = false,
                    showProducts = true,
                    showLoading = false
                )
            }
            ResultUIEvent.Loading -> {
                _switchUIState.value = SwitchUIState(
                    showNotConnected = false,
                    showNothingFound = false,
                    showProducts = false,
                    showLoading = true
                )
            }

        }
    }


    sealed class ResultUIEvent {
        data class SearchSubmit(val search: String) : ResultUIEvent()
        data class ProductClicked(val productId: String) : ResultUIEvent()
        object Connection : ResultUIEvent()
        object NothingFound : ResultUIEvent()
        object ShowProducts : ResultUIEvent()
        object Loading : ResultUIEvent()

    }

    sealed class ResultListChannel {
        data class Navigate(val navArg: String) : ResultListChannel()
        data class Message(val message: String) : ResultListChannel()
    }

    data class ResultsUIState(
        val product: PagingData<Product>
    )

    data class SwitchUIState(
        val showNotConnected: Boolean = false,
        val showNothingFound: Boolean = false,
        val showProducts: Boolean = true,
        val showLoading: Boolean = false
    )

}