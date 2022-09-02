package android.ptc.com.ptcflixing.ui.search

import android.os.Bundle
import android.ptc.com.ptcflixing.R
import android.ptc.com.ptcflixing.core.Constants.Companion.EMPTY_SEARCH_MESSAGE
import android.ptc.com.ptcflixing.databinding.FragmentSearchPageBinding
import android.ptc.com.ptcflixing.ui.utils.ISearch
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search_page), ISearch {
    private val searchViewModel by activityViewModels<SearchViewModel>()

    private var _binding: FragmentSearchPageBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSearchPageBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.searchChannel.collectLatest { channel ->
                    when (channel) {
                        is SearchViewModel.SearchChannel.Navigate -> {
                            val action =
                                SearchFragmentDirections.actionSearchFragmentToResultListFragment(
                                    channel.searchQuery
                                )
                            findNavController().navigate(action)
                        }
                        is SearchViewModel.SearchChannel.Message -> {
                            Snackbar.make(requireView(), channel.message, Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }

        binding.apply {
            searchI = this@SearchFragment
            binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) search()
                    return true
                }

                override fun onQueryTextChange(newText: String?) = false
            })
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun search() {
        binding.searchBar.clearFocus()
        val query = binding.searchBar.query
        if (query.isBlank()) {
            searchViewModel.onEvent(SearchViewModel.SearchEvent.Message(EMPTY_SEARCH_MESSAGE))
        } else {
            searchViewModel.onEvent(SearchViewModel.SearchEvent.SearchSubmit(binding.searchBar.query.toString()))
        }
    }
}