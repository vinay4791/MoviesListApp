package vinay.com.movieslistapp.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import vinay.com.movieslistapp.R

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions.placeholderOf(progressDrawable).error(R.mipmap.ic_launcher)

    Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(uri)
            .into(this)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String) {
    view.loadImage("https://image.tmdb.org/t/p/w780" +url, getProgressDrawable(view.context))
}