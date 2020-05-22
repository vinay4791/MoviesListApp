package vinay.com.movieslistapp.util

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val requestOptions = RequestOptions().fitCenter()
    Glide.with(context).load(uri).apply(requestOptions)
            .into(this)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String) {

    if (!TextUtils.isEmpty(url)) {
        url?.let {  view.loadImage("https://image.tmdb.org/t/p/w780" +url, getProgressDrawable(view.context)) }
    }


   // https://image.tmdb.org/t/p/w1920_and_h800_multi_faces
    //https://image.tmdb.org/t/p/w780
    //https://image.tmdb.org/t/p/w500/
}