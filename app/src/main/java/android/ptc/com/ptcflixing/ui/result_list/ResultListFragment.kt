package android.ptc.com.ptcflixing.ui.result_list

import android.os.Bundle
import android.ptc.com.ptcflixing.R
import android.ptc.com.ptcflixing.core.ConnectionLiveData
import android.ptc.com.ptcflixing.databinding.FragmentResultListPageBinding
import android.ptc.com.ptcflixing.domain.model.CurrencyConfig
import android.ptc.com.ptcflixing.domain.model.Product
import android.ptc.com.ptcflixing.ui.SharedViewModel
import android.ptc.com.ptcflixing.ui.result_list.ResultListViewModel.ResultListChannel
import android.ptc.com.ptcflixing.ui.result_list.ResultListViewModel.ResultUIEvent
import android.ptc.com.ptcflixing.ui.utils.IFormat
import android.ptc.com.ptcflixing.ui.utils.OnItemClickListener
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class ResultListFragment : Fragment(R.layout.fragment_result_list_page),
    OnItemClickListener, IFormat {

    private val resultListViewModel by activityViewModels<ResultListViewModel>()
    private val sharedViewModel by activityViewModels<SharedViewModel>()
    private val args by navArgs<ResultListFragmentArgs>()

    private var _binding: FragmentResultListPageBinding? = null
    private val binding get() = _binding!!

    private lateinit var connectionLiveData: ConnectionLiveData

    private lateinit var resultListAdapter: ResultListAdapter

    private lateinit var mainToolbar: MaterialToolbar

    private val formatPercentage by lazy {
        NumberFormat.getPercentInstance()
    }
    private val formatCurrency by lazy {
        NumberFormat.getCurrencyInstance()
    }

    private val decimalFormatSymbols by lazy {
        DecimalFormatSymbols()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentResultListPageBinding.bind(view)
        //SetUp
        connectionLiveData = ConnectionLiveData(requireContext())
        resultListViewModel.onEvent(ResultUIEvent.SearchSubmit(args.searchQuery))
        mainToolbar =
            requireActivity().findViewById(R.id.mainToolbar)
        mainToolbar.title = args.searchQuery

        binding.apply {
            viewModel = resultListViewModel
            lifecycleOwner = viewLifecycleOwner

            resultListAdapter = ResultListAdapter(
                this@ResultListFragment,
                this@ResultListFragment,
                sharedViewModel.state.value.currencyConfig
            )

            resultListRecyclerView.apply {
                setHasFixedSize(true)
                adapter = resultListAdapter
                layoutManager = GridLayoutManager(context, 2)
            }

        }



        binding.searchBarResults.apply {
            setQuery(args.searchQuery, false)

            setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        if (query != null) {
                            resultListViewModel.onEvent(
                                ResultUIEvent.SearchSubmit(
                                    query
                                )
                            )
                            mainToolbar.title = query
                        }

                        return true
                    }

                    override fun onQueryTextChange(newText: String?) = false
                }
            )
        }



        resultListViewModel.state.observe(viewLifecycleOwner) { state ->
            val products = state.product
            resultListAdapter.submitData(viewLifecycleOwner.lifecycle, products)
        }
        connectionLiveData.observe(viewLifecycleOwner) { isConnected ->
            resultListAdapter.addLoadStateListener { loadState ->

                val isNotLoading = loadState.source.refresh is LoadState.NotLoading
                val isEndOfPagination = loadState.append.endOfPaginationReached
                val nothingInTheAdapter = resultListAdapter.itemCount < 1
                val showNothingFound =
                    isNotLoading && nothingInTheAdapter && isEndOfPagination
                val showNotConnected =
                    isNotLoading && nothingInTheAdapter && isEndOfPagination && !isConnected

                resultListViewModel.apply {
                    if (showNothingFound) onEvent(ResultUIEvent.NothingFound)
                    else if (showNotConnected) onEvent(ResultUIEvent.Connection)
                    else if (!isNotLoading) onEvent(ResultUIEvent.Loading)
                    else onEvent(ResultUIEvent.ShowProducts)
                }
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                resultListViewModel.channel.collectLatest { channel ->
                    when (channel) {
                        is ResultListChannel.Navigate -> {
                            val action =
                                ResultListFragmentDirections.actionResultListFragmentToItemDetailFragment(
                                    channel.navArg
                                )
                            findNavController().navigate(action)
                        }
                        is ResultListChannel.Message -> {
                            Snackbar.make(requireView(), channel.message, Snackbar.LENGTH_LONG)
                                .show()
                        }
                    }
                }
            }
        }
    }

    override fun onProductClick(product: Product) =
        resultListViewModel.onEvent(ResultUIEvent.ProductClicked(product.sku))

    override fun currencyFormat(int: Int, config: CurrencyConfig): String {

        val thousands = config.thousandsSep
        val symbol = config.currencySymbol
        val decimalSep = config.decimalsSep
        val decimals = config.decimals
        val iso = config.iso
        val pos = config.position


        return try {
            val decimalFormat = formatCurrency as DecimalFormat
            val formatted = decimalFormatSymbols.apply {
                currencySymbol = symbol
                decimalSeparator = decimalSep.first()
                groupingSeparator = thousands.first()
                monetaryDecimalSeparator = decimals.toChar()
            }
            decimalFormat.decimalFormatSymbols = formatted
            decimalFormat.maximumFractionDigits = pos
            decimalFormat.format(int)

        } catch (e: Exception) {
            e.printStackTrace()
            formatCurrency.apply {
                maximumFractionDigits = pos
                currency = Currency.getInstance(iso)
            }
            formatCurrency.format(int)
        }

    }

    override fun intToStringPercentage(int: Int): String {
        val fraction = int.toFloat().div(100)
        return formatPercentage.format(fraction)
    }


    override fun onStop() {
        super.onStop()
        connectionLiveData.removeObservers(viewLifecycleOwner)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}