package android.ptc.com.ptcflixing.ui.search

import android.view.SearchEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    private val _searchChannel = Channel<SearchChannel>()
    val searchChannel = _searchChannel.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.SearchSubmit -> {
                navigate(event.query)
            }
            is SearchEvent.Message -> {
                message(event.message)
            }
        }
    }

    private fun navigate(search: String) = viewModelScope.launch {
        _searchChannel.send(SearchChannel.Navigate(search))
    }

    private fun message(message: String) = viewModelScope.launch {
        _searchChannel.send(SearchChannel.Message(message))
    }

    sealed class SearchEvent {
        data class SearchSubmit(val query: String) : SearchEvent()
        data class Message(val message: String) : SearchEvent()
    }

    sealed class SearchChannel {
        data class Navigate(val searchQuery: String) : SearchChannel()
        data class Message(val message: String) : SearchChannel()
    }
}