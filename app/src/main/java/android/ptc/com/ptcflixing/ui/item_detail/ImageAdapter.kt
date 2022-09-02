import ImageAdapter.ImageViewHolder
import android.ptc.com.ptcflixing.data.local.room.entity.ImageEntity
import android.ptc.com.ptcflixing.databinding.ListItemImageBinding
import android.ptc.com.ptcflixing.ui.utils.OnItemClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter(private val imageClickListener: OnItemClickListener) :
    ListAdapter<ImageEntity, ImageViewHolder>(FavouritesDiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ListItemImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ImageViewHolder(private val binding: ListItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageEntity: ImageEntity) {
            binding.apply {
                image = imageEntity
                listener = imageClickListener

            }
        }

    }

    class FavouritesDiffUtilCallback : DiffUtil.ItemCallback<ImageEntity>() {
        override fun areItemsTheSame(oldItem: ImageEntity, newItem: ImageEntity) =
            oldItem.imageUrl == newItem.imageUrl

        override fun areContentsTheSame(oldItem: ImageEntity, newItem: ImageEntity) =
            oldItem == newItem
    }

}
