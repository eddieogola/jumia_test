package android.ptc.com.ptcflixing.ui.item_detail

import android.ptc.com.ptcflixing.core.Constants.Companion.ERROR_404_MESSAGE
import android.ptc.com.ptcflixing.core.NetworkLoading
import android.ptc.com.ptcflixing.data.local.room.entity.ProductDetailWithImages
import android.ptc.com.ptcflixing.domain.use_case.GetProductDetail
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel @Inject constructor(
    private val getProductDetail: GetProductDetail
) : ViewModel() {

    private val _state = MutableLiveData<UIState>()
    val state: LiveData<UIState> = _state

    private val _channel = Channel<UIEvent>()
    val channel = _channel.receiveAsFlow()

    fun getProduct(productId: String) = viewModelScope.launch {
        getProductDetail(productId).collect { result ->
            when (result) {
                is NetworkLoading.Error -> {
                    result.data?.let { product ->
                        _state.value = UIState(product)
                    }
                    _channel.send(UIEvent.ShowMessage(result.message ?: ERROR_404_MESSAGE, true))

                }
                is NetworkLoading.Loading -> {
                    result.data?.let { product ->
                        _state.value = UIState(product)
                    }
                }
                is NetworkLoading.Success -> {
                    result.data?.let { product ->
                        _state.value = UIState(product)
                    }
                }
            }
        }
    }

    sealed class UIEvent {
        data class ShowMessage(val message: String, val showError: Boolean = false) : UIEvent()
    }

    data class UIState(
        val productDetail: ProductDetailWithImages
    )



}