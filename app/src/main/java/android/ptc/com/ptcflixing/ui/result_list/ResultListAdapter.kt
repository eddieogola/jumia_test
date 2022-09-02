package android.ptc.com.ptcflixing.ui.result_list

import android.ptc.com.ptcflixing.databinding.ListItemResultBinding
import android.ptc.com.ptcflixing.domain.model.CurrencyConfig
import android.ptc.com.ptcflixing.domain.model.Product
import android.ptc.com.ptcflixing.ui.utils.IFormat
import android.ptc.com.ptcflixing.ui.utils.OnItemClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class ResultListAdapter(
    private val listener: OnItemClickListener,
    private val iFormat: IFormat,
    private val config: CurrencyConfig
) :
    PagingDataAdapter<Product, ResultListAdapter.ProductViewHolder>(
        PRODUCT_COMPARATOR
    ) {

    private lateinit var overdueCallback: (item: Product) -> Unit


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ListItemResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = getItem(position)

        if (currentProduct != null) {
            holder.bind(currentProduct)
        }
    }

    inner class ProductViewHolder(private val binding: ListItemResultBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(productItem: Product) {
            binding.apply {
                product = productItem
                itemClicked = listener
                format = iFormat
                configure = config
            }
        }
    }

    companion object {
        private val PRODUCT_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product) =
                oldItem.sku == newItem.sku


            override fun areContentsTheSame(oldItem: Product, newItem: Product) =
                oldItem == newItem

        }
    }

}

