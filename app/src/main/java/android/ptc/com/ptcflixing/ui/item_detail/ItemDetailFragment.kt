package android.ptc.com.ptcflixing.ui.item_detail

import ImageAdapter
import android.os.Bundle
import android.ptc.com.ptcflixing.R
import android.ptc.com.ptcflixing.data.local.room.entity.ImageEntity
import android.ptc.com.ptcflixing.databinding.FragmentItemDetailBinding
import android.ptc.com.ptcflixing.domain.model.CurrencyConfig
import android.ptc.com.ptcflixing.ui.SharedViewModel
import android.ptc.com.ptcflixing.ui.utils.IFormat
import android.ptc.com.ptcflixing.ui.utils.OnItemClickListener
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
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
class ItemDetailFragment : Fragment(R.layout.fragment_item_detail), IFormat, OnItemClickListener {
    private val viewModel by viewModels<ItemDetailViewModel>()
    private val sharedViewModel by activityViewModels<SharedViewModel>()
    private val args by navArgs<ItemDetailFragmentArgs>()

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    private val imageAdapter by lazy {
        ImageAdapter(this)
    }
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
        _binding = FragmentItemDetailBinding.bind(view)

        binding.apply {
            imageUrl = ""
            detailViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
            format = this@ItemDetailFragment
            sharedModel = sharedViewModel
            configure = sharedViewModel.state.value.currencyConfig
            itemClick = this@ItemDetailFragment


            productGallery.apply {
                setHasFixedSize(true)
                adapter = imageAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            }
        }
        val productId = args.productId
        viewModel.getProduct(productId)


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.channel.collectLatest { event ->
                    when (event) {
                        is ItemDetailViewModel.UIEvent.ShowMessage -> {
                            binding.showError = ItemDetailViewModel.UIEvent.ShowMessage(
                                event.message,
                                event.showError
                            )
                            Snackbar.make(requireView(), event.message, Snackbar.LENGTH_LONG).show()
                        }
                    }

                }
            }
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.productDetail = state.productDetail
            requireActivity().findViewById<MaterialToolbar>(R.id.mainToolbar).title =
                state.productDetail.productDetailEntity.brand
            imageAdapter.submitList(state.productDetail.images)
            if(state.productDetail.images.isNotEmpty()) {
                val firstImage = state.productDetail.images[0].imageUrl
                binding.imageUrl = firstImage
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun intToStringPercentage(int: Int): String {
        val fraction = int.toFloat().div(100)
        return formatPercentage.format(fraction)
    }

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

    override fun onImageClick(image: ImageEntity) {
        binding.imageUrl = image.imageUrl
    }
}