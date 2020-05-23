package vinay.com.movieslistapp.util

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/*
Function returns a circular progress drawable
 */
fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

/*
Function for loading an imageurl to an imageview
 */
fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val requestOptions = RequestOptions().fitCenter()
    Glide.with(context).load(uri).apply(requestOptions)
            .into(this)
}

/*
Function called when a imageurl is set to an imageview in layout
 */
@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String) {

    if (!TextUtils.isEmpty(url)) {
        url?.let { view.loadImage("https://image.tmdb.org/t/p/w780" + url, getProgressDrawable(view.context)) }
    }
}