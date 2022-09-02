package android.ptc.com.ptcflixing.ui.utils

import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class BindingAdapters {
    companion object {
        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun ImageView.loadImageFromUrl(imageUrl: String) {
            Glide
                .with(context)
                .load(imageUrl)
                .into(this)
        }

        @BindingAdapter("strikeThroughText")
        @JvmStatic
        fun TextView.strikeThrough(show: Boolean) {
            paintFlags =
                if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }


    }
}
