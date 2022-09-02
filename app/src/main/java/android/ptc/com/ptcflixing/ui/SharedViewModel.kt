package android.ptc.com.ptcflixing.ui

import android.ptc.com.ptcflixing.core.ConnectionLiveData
import android.ptc.com.ptcflixing.core.Constants.Companion.INTERNET_CONNECTION_ERROR
import android.ptc.com.ptcflixing.core.NetworkLoading
import android.ptc.com.ptcflixing.domain.model.CurrencyConfig
import android.ptc.com.ptcflixing.domain.use_case.GetConfigurations
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getConfigurations: GetConfigurations,
    private val connectionLiveData: ConnectionLiveData
) : ViewModel() {
    private val _connectionState = MutableLiveData<SharedChannel.Message>()
    val connectionState: LiveData<SharedChannel.Message> = _connectionState

    private val _channel = Channel<SharedChannel>()
    val channel = _channel.receiveAsFlow()

    init {
        viewModelScope.launch {
            connectionLiveData.asFlow().collect { isConnected ->
              if(!isConnected)  onEvent(SharedEvents.Connection(INTERNET_CONNECTION_ERROR, false))

            }
        }

    }

    private val _showSplashScreen = MutableStateFlow(true)
    val showSplashScreen = _showSplashScreen.asStateFlow()
    private val _state = MutableStateFlow(ActivityState())
    val state = _state.asStateFlow()

    fun onEvent(events: SharedEvents) {
        when (events) {
            is SharedEvents.SplashScreen -> {
                getConfig()
            }
            is SharedEvents.Connection -> {
                _connectionState.value = SharedChannel.Message(events.message, events.condition)
            }
        }
    }

    private fun getConfig() = viewModelScope.launch {
        getConfigurations().collect { response ->
            when (response) {
                is NetworkLoading.Error -> {

                }
                is NetworkLoading.Loading -> {

                }
                is NetworkLoading.Success -> {
                    _showSplashScreen.value = false
                    response.data?.let { config ->
                        _state.value = ActivityState(config)
                    }

                }
            }

        }
    }

    sealed class SharedChannel {
        data class Message(val message: String, val condition: Boolean = true) : SharedChannel()
    }


    sealed class SharedEvents {
        object SplashScreen : SharedEvents()
        data class Connection(val message: String,val condition: Boolean = true) : SharedEvents()
    }

    data class ActivityState(
        val currencyConfig: CurrencyConfig = CurrencyConfig()
    )

}